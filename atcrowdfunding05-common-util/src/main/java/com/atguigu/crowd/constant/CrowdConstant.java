package com.atguigu.crowd.constant;

public class CrowdConstant {

    public static final String MESSAGE_LOGIN_FAILED = "抱歉！账号密码错误！请重新输入！";
    public static final String MESSAGE_LOGIN_ACCT_ALREADY_IN_USE = "抱歉！这个账号已经被使用了！";
    public static final String MESSAGE_ACCESS_FORBIDEN = "请登录以后再访问！";
    public static final String MESSAGE_STRING_INVALIDATE = "字符串不合法！请不要传入空字符串！";
    public static final String MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE = "系统错误：登录账号不唯一！";
    public static final String MESSAGE_CODE_NOT_EXISTS = "验证码无效！请检查手机号是否正确或重新发送！";
    public static final String MESSAGE_CODE_INVALID = "验证码不正确！";
    public static final String MESSAGE_HEADER_PICTURE_EMPTY = "请上传头图！";
    public static final String MESSAGE_HEADER_PICTURE_UPLOAD_FAILED = "头图上传失败，请重新上传！";
    public static final String MESSAGE_DETAIL_PICTURE_EMPTY = "请上传项目详情图！";
    public static final String MESSAGE_DETAIL_PICTURE_UPLOAD_FAILED = "项目详情图上传失败，请重新上传！";
    public static final String MESSAGE_TEMP_PROJECT_MISSING = "临时存储的Project对象不存在！";

    public static final String ATTR_NAME_EXCEPTION = "exception";
    public static final String ATTR_NAME_LOGIN_ADMIN = "loginAdmin";
    public static final String ATTR_NAME_LOGIN_MEMBER = "loginMember";
    public static final String ATTR_NAME_PAGE_INFO = "pageInfo";
    public static final String ATTR_NAME_MESSAGE = "message";
    public static final String ATTR_NAME_TEMP_PROJECT = "tempProject";
    public static final String ATTR_NAME_PORTAL_DATA = "portal_data";

    public static final String REDIS_CODE_PREFIX = "REDIS_CODE_PREFIX_";

}
