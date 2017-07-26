package com.guinong.net;

import com.guinong.net.callback.IAsyncMessageCallback;
import com.guinong.net.callback.IAsyncResultCallback;
import com.guinong.net.callback.NetworkJsonCallback;
import com.guinong.net.request.AsyncRequestState;
import com.guinong.net.request.IAsyncRequestState;
import com.guinong.net.request.entitys.IRequestEntity;
import com.guinong.net.temp.OrderFullInfo;
import com.guinong.net.temp.OrderInfo;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author csn
 * @date 2017/7/25 0025 18:42
 * @content
 */
public abstract class RequestClient {

    protected abstract OkHttpClient getHttpClient();


    public IAsyncRequestState sendRequest(Request request, IAsyncMessageCallback callback, Object userState) {
        OkHttpClient client = getHttpClient();
        Call call = client.newCall(request);
        AsyncRequestState state = new AsyncRequestState(call, userState);
        call.enqueue(new NetworkJsonCallback(callback, userState));
        return state;
    }

    public  <TRequest extends IRequestEntity,TResponse> IAsyncRequestState sendRequest1(TRequest id, IAsyncResultCallback<TResponse> callback, Object userState) {

        try{

            id.verify();
        }catch (Exception err){
           // callback.onError(enw );
        }


        return null;
    }

    public IAsyncRequestState sendRequest(OrderInfo id, IAsyncResultCallback<OrderFullInfo> callback, Object userState) {
        return null;
    }

    public void abc(){


        sendRequest(new OrderInfo(), new IAsyncResultCallback<OrderFullInfo>() {
            @Override
            public void onError(NetworkException error) {

            }

            @Override
            public void onComplete(OrderFullInfo orderFullInfo, Object userState) {

            }
        },null);

    }

}
