package com.dj.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.boot.domain.loan.Track;

import java.util.List;

public interface TrackService extends IService<Track> {


    /**
     * 添加轨迹表
     * @param track
     * @throws Exception
     */
    void insertTsrack(Track track) throws Exception;

    /**
     * 根据LoanId 查询 轨迹集合
     * @return
     * @throws Exception
     */
    List<Track> findListByLoanId(Integer loanId)throws Exception;


}
