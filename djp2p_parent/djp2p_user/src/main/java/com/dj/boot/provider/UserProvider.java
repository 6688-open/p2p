package com.dj.boot.provider;


import com.dj.boot.base.BaseDataApi;
import com.dj.boot.domain.base.BaseData;
import com.dj.boot.domain.user.entry.User;
import com.dj.boot.service.UserService;
import com.dj.boot.user.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserProvider implements UserApi {
    @Autowired
    private UserService userService;


    /**
     * 查询所有的借款人
     * @return
     * @throws Exception
     */
    @Override
    public List<User> findUserList() throws Exception {
        return userService.findUserList();
    }

    @Override
    public User findUserById(Integer id) throws Exception {
        return userService.findUserById(id);
    }
}
