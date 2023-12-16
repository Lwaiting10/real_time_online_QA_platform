package com.iweb.util;

import java.util.Base64;

/**
 * @author Liu Xiong
 * @date 7/12/2023 下午3:02
 */
public class ImageUtil {
    public static String encodeImage(byte[] image) {
        return Base64.getEncoder().encodeToString(image);
    }
}
