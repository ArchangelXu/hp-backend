package tech.mingxi.hp.backend.services;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import tech.mingxi.hp.backend.utils.DateUtil;

@Slf4j
@Component
public class RequestHelperService {
	private static final int DEFAULT_PAGE_SIZE = 20;
	private static final int MAX_PAGE_SIZE = 100;
	private static final int DEFAULT_PAGE_INDEX = 1;

	public RequestHelperService(
	) {
	}

	public boolean isAllNull(Object... fields) {
		return Arrays.stream(fields).allMatch(Objects::isNull);
	}

	public boolean isAllNotNull(Object... fields) {
		return Arrays.stream(fields).allMatch((obj -> !Objects.isNull(obj)));
	}

	public Timestamp getProperUtcTimestamp(Timestamp timestamp) {
		if (timestamp != null) {
			return timestamp;
		}
		return DateUtil.getCurrentUTCTimestamp();
	}

	public int getProperPageSize(Integer size) {
		int _size = size != null ? size : DEFAULT_PAGE_SIZE;
		if (_size < 0) {
			_size = DEFAULT_PAGE_SIZE;
		} else if (_size > MAX_PAGE_SIZE) {
			_size = MAX_PAGE_SIZE;
		}
		return _size;
	}

	public int getProperPageIndex(Integer page) {
		int _size = page != null ? page : DEFAULT_PAGE_INDEX;
		if (_size < DEFAULT_PAGE_INDEX) {
			_size = DEFAULT_PAGE_INDEX;
		}
		return _size;
	}

	public String getProperColorString(String color) {
		if (color == null) {
			return null;
		}
		color = color.trim().replace("#", "");
		int length = color.length();
		if (length == 3 || length == 6 || length == 8) {
			if (length == 3) {
				color = "" + color.charAt(0) + color.charAt(0) +
						color.charAt(1) + color.charAt(1) +
						color.charAt(2) + color.charAt(2);
				length = color.length();
			}
			if (length == 6) {
				color = "FF" + color;
				length = color.length();
			}
			return "#" + color.toUpperCase();
		} else {
			return null;
		}
	}


	public String getLengthFixedLengthContent(String content, int max) {
		if (content.length() > max) {
			return content.substring(0, max - 1) + "…";
		}
		return content;
	}
}
