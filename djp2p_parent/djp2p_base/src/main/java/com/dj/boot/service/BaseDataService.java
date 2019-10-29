package com.dj.boot.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.boot.domain.base.BaseData;
import com.dj.boot.domain.user.entry.User;

import java.util.List;
import java.util.Map;

public interface BaseDataService extends IService<BaseData> {

    /**
     * 根据parentId 查询 基础数据表
     * @param parentId
     * @return
     * @throws Exception
     */
    List<BaseData> findBaseDataByParentId(Integer parentId) throws Exception;



}
