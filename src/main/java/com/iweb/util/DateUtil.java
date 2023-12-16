package com.iweb.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Liu Xiong
 * @date 13/12/2023 上午11:44
 */
public class DateUtil {
    public static String dateToString(Date date) {
        SimpleDateFormat sdf =
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(date);
    }
}
