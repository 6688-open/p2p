package com.dj.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.boot.common.constant.SystemConstant;
import com.dj.boot.domain.account.entry.Account;
import com.dj.boot.domain.loan.Loan;
import com.dj.boot.domain.money.Money;
import com.dj.boot.domain.user.entry.User;
import com.dj.boot.mapper.MoneyProjectMapper;
import com.dj.boot.service.MoneyProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class MoneyProjectServiceImpl extends ServiceImpl<MoneyProjectMapper,Loan> implements MoneyProjectService {

    @Autowired
    private MoneyProjectMapper moneyProjectMapper;

    /**
     * type 1 根据id 查询集合   type 2 查询所有 类型为4 的 复审完成的
     * @return
     * @throws Exception
     */
    @Override
    public List<Loan> findLoanList(Integer type, Integer userId) throws Exception {
        //type 1 根据userid 查询  2 查询类型4 复审完成的
        QueryWrapper<Loan> queryWrapper = new QueryWrapper<>();
        if(type == 1){
            queryWrapper.eq("user_id", userId);
        }else if(type == 2){
            queryWrapper.eq("project_status", 4);
            queryWrapper.or();
            queryWrapper.eq("project_status", 5);
            queryWrapper.or();
            queryWrapper.eq("project_status", 6);
            queryWrapper.or();
            queryWrapper.eq("project_status", 7);
            queryWrapper.or();
            queryWrapper.eq("project_status", 8);
        } else {
            queryWrapper.eq("project_status", 4);
        }
        return  this.list(queryWrapper);
    }


    /**
     * 根据id 查询Loan
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Loan findLoanById(Integer id) throws Exception {
        return this.getById(id);
    }

    /**
     * 根据id  修改
     * @param loan
     * @throws Exception
     */
    @Override
    public void updateLoanById(Loan loan) throws Exception {
        this.updateById(loan);
    }


    /**
     * 根据身份证查询    资产表
     * @return
     * @throws Exception
     */
    @Override
    public Money findMoneyByIdCard(String idCard) throws Exception {
        return moneyProjectMapper.findMoneyByIdCard(idCard);
    }

    /**
     * 根据身份证 查询 Account  支付密码
     * @param idCard
     * @return
     * @throws Exception
     */
    @Override
    public Account findAccountByIdCard(String idCard) throws Exception {
        return moneyProjectMapper.findAccountByIdCard(idCard);
    }


    /**
     * 修改余额  冻结金额
     * @throws Exception
     */
    @Override
    public void updateMoneyByIdCard(Money money) throws Exception {
        moneyProjectMapper.updateMoneyByIdCard(money);
    }


    /**
     * 根据id查询user
     * @return
     * @throws Exception
     */
    @Override
    public User findUserById(Integer id) throws Exception {
        return moneyProjectMapper.findUserById(id);
    }
}
