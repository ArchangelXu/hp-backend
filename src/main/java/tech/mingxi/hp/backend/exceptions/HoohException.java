package tech.mingxi.hp.backend.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Locale;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HoohException extends RuntimeException {

	private int errorCode;
	private HttpStatus httpStatus;

	public HoohException(HttpStatus httpStatus, int errorCode, String devMessage) {
		super(devMessage);
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
	}

	public static HoohException badRequest(String devMessage) {
		return new HoohException(
				HttpStatus.BAD_REQUEST,
				0,
				devMessage
		);
	}
}
