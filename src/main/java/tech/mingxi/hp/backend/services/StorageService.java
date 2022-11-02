package tech.mingxi.hp.backend.services;

//import com.amazonaws.HttpMethod;
//import com.amazonaws.auth.profile.ProfileCredentialsProvider;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectResult;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.net.URL;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lombok.extern.slf4j.Slf4j;
import tech.mingxi.hp.backend.exceptions.HoohException;
import tech.mingxi.hp.backend.properties.StorageProperties;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;
import tech.mingxi.hp.backend.utils.ErrorCode;

@Slf4j
@Service
public class StorageService {
	private static final String HP = "hp";
	private static final String PATH_AVATAR = "avatars";
	private static final String PATH_SOCIAL_BADGE = "social-badges";
	private static final String PATH_TEMPLATE = "templates";
	private static final String PATH_POST = "posts";
	private static final List<String> PATHS = Arrays.asList(
			PATH_AVATAR,
			PATH_SOCIAL_BADGE,
			PATH_TEMPLATE,
			PATH_POST
	);
	public static final int TYPE_AVATAR = PATHS.indexOf(PATH_AVATAR);
	public static final int TYPE_SOCIAL_BADGE = PATHS.indexOf(PATH_SOCIAL_BADGE);
	;
	public static final int TYPE_TEMPLATE = PATHS.indexOf(PATH_TEMPLATE);
	;
	public static final int TYPE_POST = PATHS.indexOf(PATH_POST);
	;
	public static final List<Integer> TYPES = Arrays.asList(
			TYPE_AVATAR,
			TYPE_SOCIAL_BADGE,
			TYPE_TEMPLATE,
			TYPE_POST
	);

	private OSS ossClient = null;

	private final String bucketName;
	private final String endPoint;

	public StorageService(
			StorageProperties properties
	) {
		bucketName = properties.getBucketName();
		endPoint = properties.getEndPoint();
		try {
			ossClient = new OSSClientBuilder().build(endPoint, properties.getAccessKeyId(), properties.getAccessKeySecret());
			ossClient.setBucketTransferAcceleration(bucketName, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isObjectExists(String objectKey) {
		if (ObjectUtils.isEmpty(objectKey)) {
			return false;
		}
		return ossClient.doesObjectExist(bucketName, objectKey);
	}

	public String getViewingUrl(String objectKey) {
		if (ObjectUtils.isEmpty(objectKey)) {
			return null;
		}
		return getReadUrl(objectKey);
	}

	public String saveAliyunObject(String url) throws Exception {
//		String name = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("?"));
//		String ext = name.substring(name.lastIndexOf(".") + 1);
//		name = name.substring(0, name.lastIndexOf("."));
//		if (ext.equalsIgnoreCase("jpeg")) {
//			ext = "jpg";
//		}
//		String objectKey = String.format("%s/%s/%s.%s", PATH_USER_CONTENT, PATH_TEMPLATE, name, ext);
//		if (!isObjectExists(objectKey)) {
//			URL urlObj = new URL(url);
//			URLConnection connection = urlObj.openConnection();
//			InputStream inputStream = connection.getInputStream();
//			PutObjectResult result = client.putObject(bucketName, objectKey, inputStream, null);
//			log.info("result=" + result);
//		}
//		return objectKey;
		return null;
	}

	public Tuple2<String, String> getUploadingUrl(String md5, String fileExt, int resourceType) {
		if (ObjectUtils.isEmpty(md5)) {
			throw new HoohException(
					HttpStatus.BAD_REQUEST,
					ErrorCode.GENERIC_SERVER_ERROR,
					"filename cannot be null or empty"
			);
		}
		if (!TYPES.contains(resourceType)) {
			throw new HoohException(
					HttpStatus.INTERNAL_SERVER_ERROR,
					ErrorCode.GENERIC_SERVER_ERROR,
					String.format("invalid s3 path type: %d", resourceType)
			);
		}
		fileExt = fileExt.toLowerCase();
		if (TYPES.contains(resourceType)) {
			//image
			if (!Arrays.asList("jpg", "png", "jpeg").contains(fileExt)) {
				throw new HoohException(
						HttpStatus.BAD_REQUEST,
						ErrorCode.GENERIC_SERVER_ERROR,
						String.format("invalid file type: %s", fileExt)
				);
			}
			if (fileExt.equals("jpeg")) {
				fileExt = "jpg";
			}
		}
		String objectKey = String.format("%s/%s/%s.%s", HP, PATHS.get(resourceType), md5, fileExt);
		return Tuples.of(objectKey, getUploadUrl(objectKey, "image/" + (fileExt.equals("png") ? "png" : "jpeg")));
	}


	public String getReadUrl(String objectKey) {
		if (objectKey == null) {
			return null;
		}
		GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(
				bucketName,
				objectKey,
				HttpMethod.GET
		);
		req.setExpiration(getExpiration());
		URL signedUrl = ossClient.generatePresignedUrl(req);

		return signedUrl.toString();
	}

	public String getUploadUrl(String objectKey, String contentType) {
		if (objectKey == null) {
			return null;
		}
		long writeExpiration = 900L * 1000;
		GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(
				bucketName,
				objectKey,
				HttpMethod.PUT
		);
		req.setExpiration(new Date(new Date().getTime() + writeExpiration));
		req.setContentType(contentType);
		URL signedUrl = ossClient.generatePresignedUrl(req);
		return signedUrl.toString();
	}


	private Date getExpiration() {
		Date expiration = new Date();
		long expTimeMillis = Instant.now().toEpochMilli();
		expTimeMillis += 1000 * 60 * 60;//1 hour
		expiration.setTime(expTimeMillis);
		return expiration;
	}
}
