package org.wang.kuaijiback.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
@Component
public class StringUtil {
    public static boolean isEmpty(String s){
        return s==null || s.length()==0;
    }

    public static String md5(String s){
        if(StringUtil.isEmpty(s)){
            return "";
        }
        try{//采用MD5处理
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] output = md.digest(s.getBytes());//加密处理
            //将加密结果output利用Base64转成字符串输出
            String ret = Base64.encodeBase64String(output);
            return ret;
        }catch(Exception e){
            return "";
        }
    }

    //随机验证码 redis的key
    public static String captcha(){
        int i=(int)Math.random()*10000;
        return "captcha"+i;
    }
}
