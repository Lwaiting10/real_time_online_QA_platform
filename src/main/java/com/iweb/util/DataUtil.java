package com.iweb.util;

import com.iweb.bean.User;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Liu Xiong
 * @date 7/12/2023 下午2:18
 */
public class DataUtil {
    public final static String COOKIE_NAME = "remember";
    public final static String APPLICATION_COOKIE_NAME = "userCookies";

    private final static Map<Cookie, User> COOKIE_USER_MAP = new HashMap<>();

    public static void add(Cookie cookie, User user) {
        COOKIE_USER_MAP.put(cookie, user);
    }

    /**
     * 根据Cookie获取用户信息
     *
     * @param cookie 用户的Cookie对象
     * @return User 用户信息，如果找不到则返回null
     */
    public static User getUserByCookie(Cookie cookie) {
        for (Map.Entry<Cookie, User> entry : COOKIE_USER_MAP.entrySet()) {
            if (entry.getKey().getValue().equals(cookie.getValue())) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * 根据Cookie移除用户
     *
     * @param cookie 要移除的Cookie对象
     */
    public static void removeUserByCookie(Cookie cookie) {
        // 迭代遍历COOKIE_USER_MAP
        Iterator<Map.Entry<Cookie, User>> iterator = COOKIE_USER_MAP.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Cookie, User> entry = iterator.next();
            Cookie c = entry.getKey();
            // 如果要移除的Cookie的值与当前Cookie的值相等
            if (cookie.getValue().equals(cookie.getValue())) {
                // 移除该键值对
                iterator.remove();
            }
        }
    }
}
