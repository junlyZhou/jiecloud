package com.company.utils.result;

import org.apache.http.HttpStatus;

/**
 * @Author: FengJie
 * @Date: 2019/3/2 0002 20:46
 * @Description:  响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(HttpStatus.SC_OK)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static Result genSuccessResult(Object data) {
        return new Result()
                .setCode(HttpStatus.SC_OK)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .setMessage(message);
    }
}
