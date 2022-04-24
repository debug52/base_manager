package com.debug52.utils.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @ClassName DecryptUtils
 * @Description:
 * @Author debug52
 * @Date 2021/4/13
 **/
@Slf4j
public class DecryptUtils {

    public static void main(String[] args) throws Exception {
//        String rsaPublic = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCt8gwr42wV+O8EVRtEBO/iHmH2USzbeVtl9JKEw/j0f3LvK3QM4mC/SXLKBpjjAKJCPxFR/nRGEcvhcJ5hhSbdL4TtkkS25+hu5Az0duqytNoxarq8yGRhWWtgq4VmjFC3HMpOKihuobH8ugJei9WrgLHZfTqlh1bsW824xDuD3wIDAQAB";
//        String body = "admin";
//        //String desKey = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
//        String desKey = "9513DADEB22748F7904E9F0E2C8195C6";
//        System.out.println(desKey);
//        String key = RSAUtils.encryptByPublicKey(desKey, rsaPublic);
//        String value = DESUtils.encrypt(body, desKey);
//        //language=JSON
//
////        System.out.println(key);
//        System.out.println(value);
//
//        String privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK3yDCvjbBX47wRVG0QE7+IeYfZRLNt5W2X0koTD+PR/cu8rdAziYL9JcsoGmOMAokI/EVH+dEYRy+FwnmGFJt0vhO2SRLbn6G7kDPR26rK02jFqurzIZGFZa2CrhWaMULccyk4qKG6hsfy6Al6L1auAsdl9OqWHVuxbzbjEO4PfAgMBAAECgYEAnS/pDdQS/XwZygl6fSoU+HpKPkO2zJJLJMQxj2Zk19VAMJ9M3MPJd7LQ133EtQYCmujRJw5RVmyImC2kAi5IIgSCss88OdwMkIKaQ0BLuJJM4uw5Ugv3icKROIQYhMLYuaD8KOUULKjQyBB91wapIlEs7Scwk95RrqMpZrso8wECQQDi/skOJbhRK5yJVP5DL5CY3X1PQrR5nSudYmQqBLNPEgznJ+aimkR4GWnC3tkWVASWxDzoZpNF12XP0f9DVVlfAkEAxCv1OgWq7nWiS2Mc+C6utAi/8lD1eh+EtXKNumN6zRvkN2G0HfqPeyLvDuNFiJg1KXujrbt5p2pDPnBvvmllgQJBANKD7hwzuTN2Ga93ZnMSqqxKRUt7lnpu26MTO8eIIm8DU3oRFCDapwMmtmHmYDgH5VWZWs9BSfXspFxO6cgdYEsCQDyFf0ueUgTtn9t4QYjEBjwe8vEUTxeVP8EnDViytkEvWLJJX2NcWQXZWj2SmnIw2z3oGjBQRZED9Kj/0UnChIECQAxP4xJkDlzAhDIbl/sGRh431Pu/JYbVbyr9KFaZ7fuZcdFGwC7oe/Svx19WNO2eJjnwaDvJtMnYhUaz5yrXoDg=";
//        //解密
//        String content = getDesPrivateKey(key,privateKey);
//        String parameterStr = getParameterStr("0NboajC3fgQ=", content);
//        System.out.println("解密后"+content+"======"+parameterStr);
        String password = encryption("OphHfZDC6ILdfZ423M+QcA==");
        System.out.println("密码是" + password);
    }

    public static String[] decryptParams(Map<String, String> params, String RSAPrivateKey) {
        String encryptData = params.get("value");
        String wk = params.get("key");
        if (wk != null && !"".equals(wk)) {
            String desPrivateKey = getDesPrivateKey(wk, RSAPrivateKey);
            String desParams = "";
            if (encryptData != null && !"".equals(encryptData)) {
                desParams = getParameterStr(encryptData, desPrivateKey);
            }
            return new String[]{desParams, desPrivateKey};
        } else {
            return null;
        }
    }

    //加密
    public static String encryption(String password) throws Exception {
        String rsaPublic = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCt8gwr42wV+O8EVRtEBO/iHmH2USzbeVtl9JKEw/j0f3LvK3QM4mC/SXLKBpjjAKJCPxFR/nRGEcvhcJ5hhSbdL4TtkkS25+hu5Az0duqytNoxarq8yGRhWWtgq4VmjFC3HMpOKihuobH8ugJei9WrgLHZfTqlh1bsW824xDuD3wIDAQAB";
        String desKey = "9513DADEB22748F7904E9F0E2C8195C6";
        System.out.println(desKey);
        String key = RSAUtils.encryptByPublicKey(desKey, rsaPublic);
        String value = DESUtils.encrypt(password, desKey);
        return value;
    }

    public static String getDesPrivateKey(String str, String rsaPrivateKey) {
        try {
            return RSAUtils.decryptByPrivateKey(str, rsaPrivateKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getParameterStr(String str, String desPrivateKey) {
        return DESUtils.decrypt(str, desPrivateKey);
    }
}
