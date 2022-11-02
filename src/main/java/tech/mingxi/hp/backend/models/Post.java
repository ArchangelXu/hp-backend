package tech.mingxi.hp.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.mingxi.hp.backend.models.base.BaseProjection;
import tech.mingxi.hp.backend.utils.DateUtil;

@Component
@Entity
@Getter
@Setter
@ToString
@DynamicUpdate
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Table(name = "posts", schema = "hp")
public class Post implements Serializable {
	public static final int DEVICE_TYPE_ANDROID = 0;
	public static final int DEVICE_TYPE_IOS = 1;
	public static final int DEVICE_TYPE_OTHER = 2;
	@Id
	@Column
	private String id;
	@Column(name = "image_key")
	private String imageKey;
	@Column(name = "device_type")
	private int deviceType;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_at")
	private Timestamp createdAt;


	@Transient
	private String imageUrl;


	@PrePersist
	void createdAt() {
		id = UUID.randomUUID().toString();
		this.createdAt = DateUtil.getCurrentUTCTimestamp();
	}

	@Projection(types = {Post.class})
	public interface SimpleProjection extends BaseProjection {
		String getId();

		String getImageUrl();

		int getDeviceType();

		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		Timestamp getCreatedAt();
	}
}

