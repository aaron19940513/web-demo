package com.sam.demo.base;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class ResultEntity {

    private static final String SUCCESS = "200";

    private static final String FAIL = "500";

    private Object data;

    private Error error;

    private String status;

    public static ResultEntity fail(String message,String code){
        return ResultEntity.builder().status(FAIL).error(Error.builder().code(code).message(message).build()).build();

    }

    public static ResultEntity success(Object data){
        return ResultEntity.builder().status(SUCCESS).data(data).build();
    }
    @Builder
    @Data
    private static class Error{
        private String message;

        private String code;
    }


}
