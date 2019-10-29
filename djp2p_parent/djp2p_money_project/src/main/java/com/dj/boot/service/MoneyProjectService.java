package com.dj.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.boot.domain.account.entry.Account;
import com.dj.boot.domain.loan.Loan;
import com.dj.boot.domain.money.Money;
import com.dj.boot.domain.user.entry.User;

import java.util.List;

public interface MoneyProjectService extends IService<Loan> {

    /**
     * type 1 根据id 查询集合   type 2 查询所有 类型为4 的 复审完成的
     * @return
     * @throws Exception
     */
    List<Loan> findLoanList(Integer type, Integer userId) throws Exception;

    /**
     * 根据id 查询Loan
     * @param id
     * @return
     * @throws Exception
     */
    Loan findLoanById(Integer id)throws Exception;

    /**
     * 根据id  修改
     * @param loan
     * @throws Exception
     */
    void updateLoanById(Loan loan)throws Exception;

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
