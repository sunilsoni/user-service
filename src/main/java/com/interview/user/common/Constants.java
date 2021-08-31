package com.interview.user.common;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final String PASSWORD_RESET_CACHE_PREFIX = "authentication:reset_code:";
    public static final String PROFILE_CACHE_PREFIX = "authentication:profile:";
    public static final String ACTIVE_PROFILE_CACHE_PREFIX = "authentication:active_profile:";
    public static final String TOKEN_CACHE_PREFIX = "authentication:token:";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String MEDIATYPE = "application/json";
    public static final String USER_NAME_KEY = "X-OpenAM-Username";
    public static final String PASSWORD_KEY = "X-OpenAM-Password";
    public static final String API_VERSION_KEY = "Accept-API-Version";
    public static final String API_VERSION_VALUE = "resource=2.0, protocol=1.0";
    public static final String API_VERSION_VALUE_AUTH = "resource=3.1, protocol=1.0";
    public static final String API_VERSION_PROTOCOL_2_1 = "protocol=2.1,resource=3.0";
    public static final String API_VERSION_PROTOCOL_1_0 = "protocol=1.0,resource=3.0";
    public static final String ADMIN_TOKEN_KEY = "openam_sso";
    public static final String HTTP_CLIENT_ERROR_EXCEPTION = "HttpClientErrorException: ";
    public static final String URI_SEPARATOR = "/";
    public static final String AUTHENTICATE_USER_PATH = "/authenticate";
    public static final String CREATE_USER_PATH = "/users/?_action=create";
    public static final String GET_USER_DETAILS_PATH = "/users?_action=idFromSession";
    public static final String USER_PATH = "/users/";
    public static final String AUTH_SESSION_PATH = "/sessions/?_action=getSessionInfo";
    public static final String INVALIDATE_ACTION = "/sessions/?_action=logout";
    public static final String LIST_REALMS_PATH = "/global-config/realms?_fields=name&_queryFilter=true";
    public static final int MIN_CODE_LENGTH = 8;
    public static final int RECURSION_MAX_VALUE = 3;
    public static final int MAX_CODE_LENGTH = 10;
    public static final int USERS_BY_CORP_ACCT_MAX_PAGE_SIZE = 200;
    public static final String HTTP_SCHEMA = "https://";
    public static final String EXCHANGE = "authentication";
    public static final String PROFILE_MODEL_TYPE = "profile";
    public static final String PORTAL_NAME = "portalName";
    public static final String PROFILE_DTO_TYPE = "profileDTO";
    public static final String PORTAL_IDENTIFIER_TYPE = "portalIdentifier";
    public static final String PROFILE_NAME = "profileName";
    public static final String PROFILE_STATUS = "active";
    public static final String USER_DETAILS_MODEL_TYPE = "userDetails";
    public static final String USER_DETAILS_DTO_TYPE = "userDetailsDto";
    public static final String SSO_COOKIE = "sso_cookie";
    public static final String DELETE_USER_INFO = "deleteUserInfo";
    public static final String UNASSIGNED_PROFILE_IDS = "profileIds";
    public static final String CUSTOMER = "customer";
    public static final String USERNAME = "username";
    public static final String LOGIN_URL = "loginUrl";
    public static final String FIRST_NAME = "firstName";
    public static final String LOCAL_DATE_TIME_FORMAT = "yyyyMMddHHmmss.SSS";
    public static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT);
    public static final String LOCAL_DATE_FORMAT = "yyyyMMdd";
    public static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT);
    public static final String ZONED_DATE_TIME_FORMAT = "yyyyMMddHHmmss.SSSZ";
    public static final DateTimeFormatter ZONED_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(ZONED_DATE_TIME_FORMAT);
    private Constants() {
    }
}
