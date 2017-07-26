package com.guinong.net.callback;

import com.guinong.net.NetworkMessage;

/**
 * @author csn
 * @date 2017/7/25 0025 18:22
 * @content
 */
public interface IAsyncMessageCallback extends IAsyncCallback {

    /**
     * 完成
     *
     * @param message
     *            消息
     * @param userState
     *            用户状态
     */
    void onComplete(NetworkMessage message, Object userState);
}
