package com.debug52.gateway.utils;

/**
 * 验证字符串是否是空的工具类
 * @author debug52
 * @version 1.0
 **/
public class StringUtils {
    public static boolean isEmpty(String token){
        if (token==null||token.trim().length()==0||"".equals(token)){
            return true;
        }
        return false;

    }
}
