package com.iweb.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Liu Xiong
 * @date 13/12/2023 上午11:44
 */
public class DruidUtil {

    private static DataSource dataSource;
    static {
        InputStream inputStream =
                DruidUtil.class.getClassLoader().
                 getResourceAsStream("db.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
            dataSource =
                    DruidDataSourceFactory.createDataSource(p);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static DataSource getDataSource(){
        return dataSource;
    }

}
