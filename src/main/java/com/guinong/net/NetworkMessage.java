package com.guinong.net;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * @author csn
 * @date 2017/7/25 0025 17:58
 * @content
 */
public class NetworkMessage implements Serializable {

    private boolean success;
    private NetworkErrorInfo error;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public NetworkErrorInfo getError() {
        return error;
    }

    public void setError(NetworkErrorInfo error) {
        this.error = error;
    }

    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    /**
     *
     * @param resultClass
     * @param <TResult>
     * @return
     */
    public <TResult> TResult ToResult(Class<TResult> resultClass){
        if(result==null){
            return  null;
        }
        if(result instanceof String){
            Gson gson = new Gson();
            return gson.fromJson(result.toString(), resultClass);
        }
        else {
        return  null;
        }
    }
}
