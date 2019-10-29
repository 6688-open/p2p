package com.dj.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.boot.domain.account.entry.Account;
import com.dj.boot.domain.loan.Exp;
import com.dj.boot.domain.loan.Loan;
import com.dj.boot.domain.money.Money;
import com.dj.boot.mapper.ExpMapper;
import com.dj.boot.mapper.MoneyProjectMapper;
import com.dj.boot.service.ExpService;
import com.dj.boot.service.MoneyProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ExpServiceImpl extends ServiceImpl<ExpMapper, Exp> implements ExpService {



    /**
     * 添加账单
     * @param expList
     * @throws Exception
     */
    @Override
    public void insertExp(List<Exp> expList) throws Exception {
        this.saveBatch(expList);
    }

    /**
     * 根据loanid 查询账单集合
     * @return
     * @throws Exception
     */
    @Override
    public List<Exp> findListByLoanId(Integer loanId) throws Exception {
        QueryWrapper<Exp> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loan_id", loanId);
        return this.list(queryWrapper);
    }



    /**
     * 根据id 查询账单
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Exp findExpById(Integer id) throws Exception {
        return this.getById(id);
    }

    /**
     * 根据id 修改状态
     * @param exp
     * @throws Exception
     */
    @Override
    public void updateExpById(Exp exp) throws Exception {
        this.updateById(exp);
    }


}
