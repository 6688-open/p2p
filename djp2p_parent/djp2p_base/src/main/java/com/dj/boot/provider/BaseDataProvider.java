package com.dj.boot.provider;


import com.dj.boot.base.BaseDataApi;
import com.dj.boot.domain.base.BaseData;
import com.dj.boot.domain.user.entry.User;
import com.dj.boot.service.BaseDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class BaseDataProvider implements BaseDataApi {
    @Autowired
    private BaseDataService baseDataService;


    @Override
    public String sayHello(String msg) { //调本service 方法baseDataService
        return null;
    }

    @Override
    public List<BaseData> findBaseDataByParentId(Integer parentId) throws Exception {
        return baseDataService.findBaseDataByParentId(parentId);
    }
}
