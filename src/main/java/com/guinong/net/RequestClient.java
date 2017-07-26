package com.guinong.net;

import com.google.gson.Gson;
import com.guinong.net.callback.IAsyncMessageCallback;
import com.guinong.net.callback.IAsyncResultCallback;
import com.guinong.net.callback.NetworkJsonCallback;
import com.guinong.net.request.AsyncRequestState;
import com.guinong.net.request.IAsyncRequestState;
import com.guinong.net.verify.VerifyManager;
import java.lang.reflect.Type;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author csn
 * @date 2017/7/25 0025 18:42
 * @content
 */
public abstract class RequestClient {

    /**
     *
     */
    public static final MediaType APPLICATION_JSON = MediaType.parse("application/json; charset=utf-8");

    protected abstract OkHttpClient getHttpClient();

    public static boolean isUnitTest = true;

    /**
     * @return
     */
    protected Gson createGson() {
        return new Gson();
    }

    /**
     * 调查用请求
     *
     * @param request
     * @param callback
     * @param userState
     * @return
     */
    public IAsyncRequestState callRequest(Request request, IAsyncMessageCallback callback, Object userState) {
        ExceptionUtils.checkNotNull(request, "request");
        ExceptionUtils.checkNotNull(callback, "callback");
        OkHttpClient client = getHttpClient();
        Call call = client.newCall(request);
        AsyncRequestState state = new AsyncRequestState(call, userState);
        call.enqueue(new NetworkJsonCallback(createGson(), callback, userState));
        return state;
    }

    /**
     * @param responseClass
     * @param url
     * @param model
     * @param callback
     * @param userState
     * @param <TResponse>
     * @return
     */
    protected <TResponse> IAsyncRequestState postRequest(final Class<TResponse> responseClass, String url,
                                                         Object model, final IAsyncResultCallback<TResponse> callback, Object userState) {
        ExceptionUtils.checkNotNull(responseClass, "responseClass");
        ExceptionUtils.checkNotNull(url, "url");
        ExceptionUtils.checkNotNull(model, "model");
        ExceptionUtils.checkNotNull(callback, "callback");
        final Gson gson = createGson();
        final IAsyncMessageCallback msgCallBack = new IAsyncMessageCallback() {
            @Override
            public void onError(NetworkException error, Object userState) {
                callback.onError(error, userState);
            }
            @Override
            public void onComplete(NetworkResultMessage message, Object userState) {
                callback.onComplete(message.ToResult(responseClass, gson), userState);
            }
        };
        try {
            VerifyManager.validate(model);
        } catch (Exception err) {
            msgCallBack.onError(new NetworkException(25555, err.getMessage(), null, err), userState);
            return null;
        }
        String json = gson.toJson(model);
        RequestBody body = RequestBody.create(APPLICATION_JSON, json);
        Request req = new Request.Builder().url(url).post(body).build();
        return callRequest(req, msgCallBack, userState);
    }

    /**
     * @param responseType
     * @param url
     * @param model
     * @param callback
     * @param userState
     * @param <TResponse>
     * @return
     */
    protected <TResponse> IAsyncRequestState postRequest(final Type responseType, String url,
                                                         Object model, final IAsyncResultCallback<TResponse> callback, Object userState) {
        ExceptionUtils.checkNotNull(responseType, "responseType");
        ExceptionUtils.checkNotNull(url, "url");
        ExceptionUtils.checkNotNull(model, "model");
        ExceptionUtils.checkNotNull(callback, "callback");
        final Gson gson = createGson();
        final IAsyncMessageCallback msgCallBack = new IAsyncMessageCallback() {
            @Override
            public void onError(NetworkException error, Object userState) {
                callback.onError(error, userState);
            }
            @Override
            public void onComplete(NetworkResultMessage message, Object userState) {
                TResponse response = message.ToResult(responseType, gson);
                callback.onComplete(response, userState);
            }
        };
        try {
            VerifyManager.validate(model);
        } catch (Exception err) {
            msgCallBack.onError(new NetworkException(25555, err.getMessage(), null, err), userState);
            return null;
        }
        String json = gson.toJson(model);
        RequestBody body = RequestBody.create(APPLICATION_JSON, json);
        Request req = new Request.Builder().url(url).post(body).build();
        return callRequest(req, msgCallBack, userState);
    }
}
