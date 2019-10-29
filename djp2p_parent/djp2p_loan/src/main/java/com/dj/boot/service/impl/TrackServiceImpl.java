package com.dj.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.boot.domain.loan.Loan;
import com.dj.boot.domain.loan.Track;
import com.dj.boot.mapper.LoanMapper;
import com.dj.boot.mapper.TrackMapper;
import com.dj.boot.service.LoanService;
import com.dj.boot.service.TrackService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImpl extends ServiceImpl<TrackMapper, Track> implements TrackService {


    /**
     * 添加轨迹表
     * @param track
     * @throws Exception
     */
    @Override
    public void insertTsrack(Track track) throws Exception {
        this.save(track);
    }

    /**
     * 根据LoanId 查询 轨迹集合
     * @return
     * @throws Exception
     */
    @Override
    public List<Track> findListByLoanId(Integer loanId) throws Exception {
        QueryWrapper<Track> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loan_id", loanId);
        return this.list(queryWrapper);
    }
}
