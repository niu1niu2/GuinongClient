package com.guinong.net;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;

/**
 * @author csn
 * @date 2017/7/26 0026 15:13
 * @content
 */
public class NetworkResultMessage<TResult> extends NetworkMessage {

    public NetworkResultMessage() {
    }

    private TResult result;

    public TResult getResult() {
        return result;
    }

    public void setResult(TResult result) {
        this.result = result;
    }

}
