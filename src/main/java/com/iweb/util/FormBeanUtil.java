package com.iweb.util;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;
/**
 * @author Liu Xiong
 * @date 13/12/2023 上午11:44
 */
public class FormBeanUtil {

    public static <T> T formToBean(Map<String,String[]> map,
                        Class<T> tClass){
        T t= null;
        try {
            t = tClass.newInstance();
            BeanUtils.populate(t,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
