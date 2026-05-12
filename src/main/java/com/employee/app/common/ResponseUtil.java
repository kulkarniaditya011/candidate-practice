package com.employee.app.common;

import com.employee.app.response.RestApiResponse;

import java.util.Date;

public class ResponseUtil {
    public static <T> RestApiResponse<T> getResponse(T data, String message){
        return RestApiResponse.<T>builder()
                .data(data)
                .message(message)
                .timeStamp(new Date())
                .build();
    }

    public static <T> RestApiResponse<T> getResponse(Object messages){
        return RestApiResponse.<T>builder()
                .message(messages)
                .timeStamp(new Date())
                .build();
    }

    public static Long getAuthUser(){
        return 1l;
    }
}
