package com.dj.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.boot.common.constant.SystemConstant;
import com.dj.boot.domain.money.Money;
import com.dj.boot.domain.user.entry.User;
import com.dj.boot.mapper.MoneyMapper;
import com.dj.boot.mapper.UserMapper;
import com.dj.boot.service.MoneyService;
import com.dj.boot.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class MoneyServiceImpl extends ServiceImpl<MoneyMapper, Money> implements MoneyService {


    /**
     * 通过身份证获取数据
     * @param idCard
     * @return
     * @throws Exception
     */
    @Override
    public Money findMoneyByIdCard(String idCard) throws Exception {
        QueryWrapper<Money>  queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id_card", idCard);
        return this.getOne(queryWrapper);
    }

    /**
     * 添加或者修改
     * @param money
     * @throws Exception
     */
    @Override
    public void insertOrUpdate(Money money) throws Exception {
        this.saveOrUpdate(money);
    }


    /**
     * 添加
     * @param money
     * @throws Exception
     */
    @Override
    public void insert(Money money) throws Exception {
        this.save(money);
    }


}
