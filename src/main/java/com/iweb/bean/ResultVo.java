package com.iweb.bean;

import lombok.Data;

/**
 * 用于后台给前端返回的结果集对象
 *
 * @author Liu Xiong
 * @date 13/12/2023 下午4:31
 */
@Data
public class ResultVo<T> {
    private boolean ok;// 操作是否成功
    private String mess;// 返回消息
    private T t;// 返回的数据
}
