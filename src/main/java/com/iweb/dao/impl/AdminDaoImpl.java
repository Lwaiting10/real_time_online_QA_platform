package com.iweb.dao.impl;

import com.iweb.bean.Admin;
import com.iweb.dao.AdminDao;
import com.iweb.util.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

/**
 * @author Liu Xiong
 * @date 16/12/2023 下午7:59
 */
public class AdminDaoImpl implements AdminDao {
    private final QueryRunner qr =
            new QueryRunner(DruidUtil.getDataSource());

    @Override
    public boolean update(Admin admin) {
        String sql = "update admin set username=?,password=? where adminId = ?";
        try {
            int update = qr.update(sql, admin.getUsername(), admin.getPassword(), admin.getAdminId());
            return update > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
