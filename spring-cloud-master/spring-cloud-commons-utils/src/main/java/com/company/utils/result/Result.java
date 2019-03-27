package com.company.utils.result;

import com.alibaba.fastjson.JSON;

/**
 * @Author: FengJie
 * @Date: 2019/3/2 0002 20:46
 * @Description:  统一api结果封装
 */
public class Result {
    /**
     *状态码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;

    public Result setCode(Integer code) {
        this.code =  code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
