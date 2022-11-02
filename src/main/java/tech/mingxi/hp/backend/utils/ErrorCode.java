package tech.mingxi.hp.backend.utils;

public class ErrorCode {
	//region system
	/**
	 * generic error type
	 */
	public static final int GENERIC_SERVER_ERROR = 10001;
	/**
	 * invalid token
	 */
	public static final int INVALID_TOKEN = 10002;
	/**
	 * not found
	 */
	public static final int RESOURCE_NOT_FOUND = 10003;
	/**
	 * invalid user-agent
	 */
	public static final int INVALID_USER_AGENT = 10004;
	/**
	 * bad request
	 */
	public static final int BAD_REQUEST = 10005;
	/**
	 * invalid value
	 */
	public static final int INVALIE_VALUE = 10006;
	//endregion

	//region user
	/**
	 * mobile and password not matching
	 */
	public static final int INVALID_MOBILE_AND_PASSWORD = 20001;
	/**
	 * register token not found
	 */
	public static final int REGISTER_TOKEN_NOT_FOUND = 20002;
	/**
	 * invalid validation code
	 */
	public static final int INVALID_VALIDATION_CODE = 20003;
	/**
	 * invalid validation code
	 */
	public static final int MOBILE_NOT_REGISTERED = 20004;
	/**
	 * invalid mobile number
	 */
	public static final int INVALID_MOBILE_NUMBER = 20005;
	/**
	 * user has been banned
	 */
	public static final int IS_BANNED_USER = 20006;
	/**
	 * user has closed account
	 */
	public static final int IS_CLOSED_USER = 20007;
	/**
	 * user already registered
	 */
	public static final int USERNAME_ALREADY_REGISTERED = 20008;
	/**
	 * email is already validated
	 */
	public static final int EMAIL_ALREADY_VALIDATED = 20009;
	/**
	 * email not found or not verified
	 */
	public static final int EMAIL_NOT_VERIFIED = 20010;
	/**
	 * mobile already bound to another user
	 */
	public static final int MOBILE_ALREADY_BOUND = 20011;
	/**
	 * user from access token not found
	 */
	public static final int INVALID_USER_FROM_TOKEN = 20012;
	/**
	 * invalid website url
	 */
	public static final int INVALID_WEBSITE_URL = 20013;
	/**
	 * invalid username
	 */
	public static final int INVALID_USERNAME = 20014;
	/**
	 * invalid password
	 */
	public static final int INVALID_PASSWORD = 20015;
	/**
	 * user change name too often
	 */
	public static final int EDIT_NAME_TOO_OFTEN = 20016;
	/**
	 * not admin
	 */
	public static final int USER_IS_NOT_ADMIN = 20017;
	//endregion
	//region social
	public static final int ALREADY_VOTED_POST = 30000;
	/**
	 * post not in waiting list
	 */
	public static final int POST_NOT_IN_WAITING_LIST = 30001;

	//endregion
	//region wallet
	public static final int INSUFFICIENT_FUNDS = 40000;

	//endregion
}
