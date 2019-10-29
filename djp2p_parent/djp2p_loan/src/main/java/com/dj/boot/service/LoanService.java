package com.dj.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.boot.domain.loan.Loan;
import com.dj.boot.domain.loan.Track;

import java.util.List;

public interface LoanService extends IService<Loan> {

    /**
     * 发标 保存数据  新增
     * @throws Exception
     */
     void insertLoan(Loan loan) throws Exception;

    /**
     * 根据id 查询
     * @return
     * @throws Exception
     */
     Loan findLoanById(Integer id)throws Exception;

    /**
     * 修改loan    添加轨迹表
     * @param loan
     * @param track
     * @throws Exception
     */
     void updateLoanById(Loan loan, Track track)throws Exception;

    /**
     * 展示集合  风控表   项目管理表  4
     * @return
     * @throws Exception
     */
     List<Loan> findList(Integer type)throws Exception;
}
