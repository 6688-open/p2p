package com.dj.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.boot.domain.account.entry.Account;
import com.dj.boot.domain.loan.Loan;
import com.dj.boot.domain.money.Money;
import com.dj.boot.domain.user.entry.User;

public interface MoneyProjectMapper extends BaseMapper<Loan> {

    /**
     * 根据身份证查询    资产表
     * @return
     * @throws Exception
     */
    Money findMoneyByIdCard(String idCard) throws Exception;

    /**
     * 根据身份证 查询 Account  支付密码
     * @param idCard
     * @return
     * @throws Exception
     */
    Account findAccountByIdCard(String idCard)throws Exception;

    /**
     * 修改余额  冻结金额
     * @throws Exception
     */
    void updateMoneyByIdCard(Money money) throws  Exception;


    /**
     * 根据id查询user
     * @return
     * @throws Exception
     */
    User findUserById(Integer id) throws Exception;
}
