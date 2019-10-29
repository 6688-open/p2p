package com.dj.boot.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.boot.domain.account.entry.Account;
import com.dj.boot.domain.user.entry.User;

public interface AccountService extends IService<Account> {


    /**
     * 开户 新增开户信息   并修改user信息
     * @throws Exception
     */
    void insertAccount(Account account) throws Exception;

    /**
     * 根据身份证去重 account
     * @param idCard
     * @return
     * @throws Exception
     */
    Account findAccountByIdCard(String idCard)throws Exception;



}
