package com.dj.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.boot.common.constant.SystemConstant;
import com.dj.boot.domain.account.entry.Account;
import com.dj.boot.domain.money.Money;
import com.dj.boot.domain.user.entry.User;
import com.dj.boot.mapper.AccountMapper;
import com.dj.boot.service.AccountService;
import com.dj.boot.service.MoneyService;
import com.dj.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {


    @Autowired
    private UserService userService;
    @Autowired
    private MoneyService moneyService;
    /**
     * 开户 新增开户信息   并修改user信息
     * @throws Exception
     */
    @Override
    public void insertAccount(Account account) throws Exception {
        this.save(account);
        //修改user并修改user用户的状态  开户2   银行卡号保存
        User user = userService.findUserByIdCardAndName(account.getClientUserName(), account.getIdCard());
        user.setBankCard(account.getBankCard());
        user.setIsAccount(SystemConstant.TWO);//2 开户状态
        user.setUserRole(Integer.valueOf(account.getAccountType())); //user  投资人  贷款人
        userService.updateById(user);
        Money money = new Money();
        money.setBalanceMoney(SystemConstant.NUMBER_ZERO);
        money.setIdCard(account.getIdCard());
        moneyService.insert(money);
    }

    /**
     * 根据身份证去重 account
     * @param idCard
     * @return
     * @throws Exception
     */
    @Override
    public Account findAccountByIdCard(String idCard) throws Exception {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id_card", idCard);
        return this.getOne(queryWrapper);
    }
}
