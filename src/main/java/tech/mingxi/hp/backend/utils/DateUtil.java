package tech.mingxi.hp.backend.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {

	public static Timestamp get0OClockUTCTimestampOfDate(Timestamp date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTime().getTime());
	}

	public static Timestamp getCurrentUTCTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Timestamp getUtcTimestamp(Timestamp timestampBeijing) {
		if (timestampBeijing == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestampBeijing.getTime());
		calendar.add(Calendar.HOUR_OF_DAY, -8);
		return new Timestamp(calendar.getTime().getTime());
	}


	/**
	 * 获取某日0点的时间戳
	 *
	 * @param dayOffset 偏移值，单位：天
	 * @return
	 */
	public static Timestamp getDayStartDate(int dayOffset) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return getDayStartDate(timestamp, dayOffset);
	}

	/**
	 * 获取某日0点的时间戳
	 *
	 * @param timestamp 基础日期
	 * @param dayOffset 偏移值，单位：天
	 * @return
	 */
	public static Timestamp getDayStartDate(Timestamp timestamp, int dayOffset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, dayOffset);
		calendar.add(Calendar.HOUR_OF_DAY, 8);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return new Timestamp(calendar.getTime().getTime());
	}

	public static Timestamp getDateFromString(String timestamp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (timestamp != null && !timestamp.trim().isEmpty()) {
				return new Timestamp(dateFormat.parse(timestamp).getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getStringFromDate(Date timestamp) {
		return getStringFromDate(timestamp, "yyyy-MM-dd HH:mm:ss");
	}

	public static String getStringFromDate(Date timestamp, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			if (timestamp != null) {
				return dateFormat.format(timestamp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
