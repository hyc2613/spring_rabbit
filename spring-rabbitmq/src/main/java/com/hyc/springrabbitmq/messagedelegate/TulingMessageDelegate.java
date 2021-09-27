package com.hyc.springrabbitmq.messagedelegate;

import com.hyc.springrabbitmq.entity.Order;

import java.io.File;
import java.util.Map;

public class TulingMessageDelegate {

    public void handleMessage(String msg) {
        System.out.println("tulingMessageDelegate........handleMessage:"+msg);
    }

    public void consumerMessage(String msg) {
        System.out.println("tulingMessageDelegate........consumerMessage:"+msg);
    }

    public void consumeTopicMessage(String msg) {
        System.out.println("tulingMessageDelegate........consumeTopicMessage:"+msg);
    }

    public void consumeJsonMessage(Map jsonMsg) {
        System.out.println("tulingMessageDelegate........consumeJsonMessage:"+jsonMsg);
    }

    public void consumeJavaObjMessage(Order order) {
        System.out.println("tulingMessageDelegate........处理java对象:"+order);
    }

    public void consumerFileMessage(File file) {
        System.out.println("TulingMsgDelegate========================处理文件"+file.getName());

    }
}
