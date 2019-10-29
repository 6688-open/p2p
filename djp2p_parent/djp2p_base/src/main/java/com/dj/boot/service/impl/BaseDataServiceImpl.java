package com.dj.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.boot.domain.base.BaseData;
import com.dj.boot.mapper.BaseDataMapper;
import com.dj.boot.service.BaseDataService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BaseDataServiceImpl extends ServiceImpl<BaseDataMapper, BaseData> implements BaseDataService {


    /**
     * 根据parentId 查询 基础数据表
     * @param parentId
     * @return
     * @throws Exception
     */
    @Override
    public List<BaseData> findBaseDataByParentId(Integer parentId) throws Exception {
        QueryWrapper<BaseData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        return this.list(queryWrapper);
    }
}
