package com.guinong.net.callback;

import android.os.Handler;
import android.os.Looper;

import com.guinong.net.NetworkException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author csn
 * @date 2017/7/25 0025 18:13
 * @content
 */
public class NetworkJsonCallback implements Callback {

    private final Handler handler;
    private final IAsyncMessageCallback callback;
    private final Object userState;
    /**
     * @param asyncResultCallback
     */
    public NetworkJsonCallback(IAsyncMessageCallback asyncResultCallback, Object userState) {
        this.callback = asyncResultCallback;
        this.handler = new Handler(Looper.getMainLooper());
        this.userState = userState;
    }

    private void postException(final NetworkException exception) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(exception);
            }
        });
    }

    @Override
    public void onFailure(Call call, IOException e) {
        if (call.isCanceled()) {
            postException(new NetworkException(this.userState,-10000, e.getMessage(), null, e));
        } else {
            postException(new NetworkException(this.userState,-999, e.getMessage(), null, e));
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String result = response.body().string();
        if (result == null || result.trim().length() == 0) {
            postException(new NetworkException(this.userState,-588, "format error ", null, null));
            return;
        }
        JSONObject obj = null;
        try {
            obj = new JSONObject(result);
        } catch (JSONException e) {
            postException(new NetworkException(this.userState,-588, "server json format error ", e.getMessage(), e));
            return;
        }
        result = result.trim();
        if(result.charAt(0)!='{'){

        }
    }
}
