package com.iweb.util;

import java.util.UUID;

/**
 * @author Liu Xiong
 * @date 13/12/2023 上午11:44
 */
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
