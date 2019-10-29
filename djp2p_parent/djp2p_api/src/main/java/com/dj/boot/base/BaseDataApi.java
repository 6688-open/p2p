package com.dj.boot.base;



import com.dj.boot.domain.base.BaseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface BaseDataApi {
    @RequestMapping("sayHello")
    String sayHello(@RequestParam("msg") String msg);


    /**
     * 根据parentId 查询 基础数据表
     * @param parentId
     * @return
     * @throws Exception
     */
    @RequestMapping("/loan/findBaseDataByParentId")
    List<BaseData> findBaseDataByParentId(@RequestParam("parentId")Integer parentId) throws Exception;










}
