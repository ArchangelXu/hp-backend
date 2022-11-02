package tech.mingxi.hp.backend.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
	private String endPoint;
	private String bucketName;
	private String accessKeyId;
	private String accessKeySecret;
}