package com.dj.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.boot.domain.account.entry.Account;
import com.dj.boot.domain.loan.Exp;
import com.dj.boot.domain.loan.Loan;
import com.dj.boot.domain.money.Money;

import java.util.List;

public interface ExpService extends IService<Exp> {

    /**
     * 添加账单
     * @param expList
     * @throws Exception
     */
    void insertExp(List<Exp> expList) throws Exception;

    /**
     * 根据loanid 查询账单集合
     * @return
     * @throws Exception
     */
    List<Exp> findListByLoanId(Integer loanId)throws Exception;

    /**
     * 根据id 查询账单
     * @param id
     * @return
     * @throws Exception
     */
    Exp findExpById(Integer id) throws Exception;

    /**
     * 根据id 修改状态
     * @param exp
     * @throws Exception
     */
    void updateExpById(Exp exp) throws Exception;

}
