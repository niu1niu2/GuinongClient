package com.guinong.net.callback;

import com.guinong.net.NetworkException;

/**
 * @author csn
 * @date 2017/7/25 0025 18:14
 * @content
 */
public interface IAsyncCallback {

    /**
     * 异常
     *  @param error
     *            错误
     *
     */
    void onError(NetworkException error);


}
