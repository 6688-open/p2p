package com.dj.boot.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.boot.domain.money.Money;
import com.dj.boot.domain.user.entry.User;

import java.util.Map;

public interface MoneyService extends IService<Money> {


    /**
     * 通过身份证获取数据
     * @param idCard
     * @return
     * @throws Exception
     */
    Money findMoneyByIdCard(String idCard) throws Exception;

    /**
     * 添加或者修改
     * @param money
     * @throws Exception
     */
    void insertOrUpdate(Money money) throws Exception;
    /**
     * 添加
     * @param money
     * @throws Exception
     */
    void insert(Money money) throws Exception;


}
