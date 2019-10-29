package com.dj.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.boot.common.constant.SystemConstant;
import com.dj.boot.domain.loan.Loan;
import com.dj.boot.domain.loan.Track;
import com.dj.boot.mapper.LoanMapper;
import com.dj.boot.service.LoanService;
import com.dj.boot.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl extends ServiceImpl<LoanMapper, Loan> implements LoanService {
    @Autowired
    private TrackService trackService;

    /**
     * 发标 保存数据  新增
     * @throws Exception
     */
    @Override
    public void insertLoan(Loan loan) throws Exception {
        this.save(loan);
    }

    /**
     * 根据id 查询
     * @return
     * @throws Exception
     */
    @Override
    public Loan findLoanById(Integer id) throws Exception {
        return this.getById(id);
    }

    /**
     * 修改loan    添加轨迹表
     * @param loan
     * @param track
     * @throws Exception
     */
    @Override
    public void updateLoanById(Loan loan, Track track) throws Exception {
        //修改状态
        this.updateById(loan);
        //添加轨迹
        trackService.insertTsrack(track);
    }

    /**
     * 展示集合  风控表 查询全部      项目管理表  project_status = 4
     * @return
     * @throws Exception
     */
    @Override
    public List<Loan> findList(Integer type) throws Exception {
        if(type == SystemConstant.TYPE_NUMBER){
            return this.list();
        } else{
            QueryWrapper<Loan> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("project_status", SystemConstant.FOUR);
            return this.list(queryWrapper);
        }

    }
}
