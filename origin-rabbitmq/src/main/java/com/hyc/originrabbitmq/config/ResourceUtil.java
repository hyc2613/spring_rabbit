package com.hyc.originrabbitmq.config;

import java.util.ResourceBundle;

public class ResourceUtil {

    private ResourceUtil() {
    }

    public static String getRabbitUri() {
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        return bundle.getString("rabbit.uri");
    }

}
