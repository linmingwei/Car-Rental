package com.example.carrental.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RestResponse<T> {
    public static final String SUCCESS = "success";
    private String message;
    private T data;
    private String error;

    public static RestResponse success(){
        return successResponseBuilder().build();
    }


    public static <T> RestResponse success(T data){
        return successResponseBuilder().data(data).build();
    }

    private static RestResponseBuilder successResponseBuilder() {
        return RestResponse.builder().message(SUCCESS);
    }

    public static RestResponse error(String msg, String error){
        return errorResponseBuilder().message(msg).error(error).build();
    }

    private static RestResponseBuilder errorResponseBuilder() {
        return RestResponse.builder().message(SUCCESS);
    }
}
