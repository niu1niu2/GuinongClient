package com.guinong.net;

import com.guinong.net.callback.IAsyncResultCallback;
import com.guinong.net.test.model.OrderInfo;
import com.guinong.net.test.model.HomeRequest;
import com.guinong.net.test.model.bean.Cart;
import com.guinong.net.test.model.bean.HomeBean;
import com.guinong.net.test.model.bean.ResultBean2;
import com.guinong.net.verify.VerifyManager;

import org.junit.Test;

import java.util.List;

/**
 * @author csn
 * @date 2017/7/26 0026 11:54
 * @content
 */
public class VerifyTest {
    public static final String HOST = "http://dev.guinong.com:8810/";

    @Test
    public void notNullTest() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId("1234");
        orderInfo.setName("张三");

        VerifyManager.validate(orderInfo);

    }

    @Test
    public void homeTest() {
        HomeRequest pars = new HomeRequest();
        pars.setPageId(4);
        TestClient test = new TestClient();
        test.homeData(pars, new IAsyncResultCallback<List<ResultBean2>>() {
            @Override
            public void onComplete(List<ResultBean2> items, Object userState) {
                if (items == null) {

                }
            }

            @Override
            public void onError(NetworkException error, Object userState) {
                System.out.print(error.getMessage());
                if (error == null) {

                }
            }
        }, null);

        try {
            Thread.sleep(1000 * 60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
