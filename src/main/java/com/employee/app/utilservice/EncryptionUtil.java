package com.employee.app.utilservice;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class EncryptionUtil {
    public static String encode(String text){
       try{
           return URLEncoder.encode(text, StandardCharsets.UTF_8);
       } catch (Exception e) {
           return text;
       }
    }

    public static String decode(String text){
        try{
            return URLDecoder.decode(text, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return text;
        }
    }
}
