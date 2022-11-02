package tech.mingxi.hp.backend.services;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.util.function.Tuple2;
import tech.mingxi.hp.backend.exceptions.HoohException;
import tech.mingxi.hp.backend.utils.ErrorCode;
import tech.mingxi.hp.backend.utils.Projector;

@Slf4j
@Service
@Transactional
public class AppService {
	private final StorageService storageService;
	private final Projector projector;

	private final RequestHelperService requestHelperService;

	public AppService(
			StorageService storageService,
			Projector projector,
			RequestHelperService requestHelperService
	) {
		this.storageService = storageService;
		this.projector = projector;
		this.requestHelperService = requestHelperService;
	}


	public Tuple2<String, String> requestUploadingImageUrl(String md5, String ext, int type) {
		return storageService.getUploadingUrl(md5.trim(), ext.trim(), type);
	}

	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	@Data
	@AllArgsConstructor
	public static class RequestUploadingUrlResponse {
		private String key;
		private String uploadingUrl;
	}

	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	@Data
	public static class RequestUploadingUrlRequest {
		private @NotEmpty String md5;
		private @NotEmpty String ext;
	}
}
