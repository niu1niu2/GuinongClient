package com.guinong.net;

/**
 * @author csn
 * @date 2017/7/25 0025 18:15
 * @content
 */
public class NetworkException extends RuntimeException {

    private final int code;
    private final String detail;
    private final Object userState;

    public int getCode() {
        return code;
    }

    public String getDetail() {
        return detail;
    }

    public NetworkException(Object userState,int code, String message) {
        this(userState,code,message,null,null);
    }

    public NetworkException(Object userState,int code, String message, String detail) {
        this(userState,code,message,detail,null);
    }

    public NetworkException(Object userState,int code, String message, String detail, Throwable cause) {
        super(message, cause);
        this.userState = userState;
        this.code = code;
        this.detail = detail;
    }
}
