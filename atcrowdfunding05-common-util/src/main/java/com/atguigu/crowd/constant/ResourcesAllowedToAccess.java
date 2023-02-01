package com.atguigu.crowd.constant;

import java.util.HashSet;
import java.util.Set;

public class ResourcesAllowedToAccess {
    public static final Set<String> RESOURCES_ALLOWED_TO_ACCESS = new HashSet<String>();

    static {
        RESOURCES_ALLOWED_TO_ACCESS.add("/");
        RESOURCES_ALLOWED_TO_ACCESS.add("/auth/member/to/reg/page");
        RESOURCES_ALLOWED_TO_ACCESS.add("/auth/member/to/login/page");
        RESOURCES_ALLOWED_TO_ACCESS.add("/auth/member/logout");
        RESOURCES_ALLOWED_TO_ACCESS.add("/auth/member/do/login");
        RESOURCES_ALLOWED_TO_ACCESS.add("/auth/do/member/register");
        RESOURCES_ALLOWED_TO_ACCESS.add("/auth/member/send/short/message.json");
    }

    public static final Set<String> STATIC_RESOURCES_ALLOWED_TO_ACCESS = new HashSet<String>();

    static {
        STATIC_RESOURCES_ALLOWED_TO_ACCESS.add("bootstrap");
        STATIC_RESOURCES_ALLOWED_TO_ACCESS.add("css");
        STATIC_RESOURCES_ALLOWED_TO_ACCESS.add("fonts");
        STATIC_RESOURCES_ALLOWED_TO_ACCESS.add("img");
        STATIC_RESOURCES_ALLOWED_TO_ACCESS.add("jquery");
        STATIC_RESOURCES_ALLOWED_TO_ACCESS.add("layer");
        STATIC_RESOURCES_ALLOWED_TO_ACCESS.add("script");
        STATIC_RESOURCES_ALLOWED_TO_ACCESS.add("ztree");
    }

    /**
     * 用于判断某个ServletPath值是否对应一个静态资源
     *
     * @param servletPath
     * @return true：是静态资源, false：不是静态资源
     */
    public static boolean isStaticResourcesAllowedToAccess(String servletPath) {
        // 1.排除字符串无效的情况
        if (servletPath == null || servletPath.length() == 0) {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        // 2.根据“/”拆分ServletPath字符串
        String[] split = servletPath.split("/");

        // 3.考虑到第一个斜杠左边经过拆分后得到一个空字符串是数组的第一个元素，所以需要使用下标1取第二个元素
        String firstLevelPath = split[1];

        // 4.判断是否在集合中
        return STATIC_RESOURCES_ALLOWED_TO_ACCESS.contains(firstLevelPath);

    }

    // 用来测试isResourcesAllowedToAccess方法
//    public static void main(String[] args) {
//        boolean result = isResourcesAllowedToAccess("/");
//        System.out.println("result = " + result);
//    }

}
