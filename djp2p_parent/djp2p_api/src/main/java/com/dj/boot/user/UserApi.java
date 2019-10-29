package com.dj.boot.user;



import com.dj.boot.domain.base.BaseData;
import com.dj.boot.domain.user.entry.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface UserApi {
   /* @RequestMapping("sayHello")
    String sayHello(@RequestParam("msg") String msg);


    *//**
     * 查询所有的借款人
     * @return
     * @throws Exception
     */
    @RequestMapping("/loan/findUserList")
    List<User> findUserList() throws Exception;

    /**
     * 查询  根据id查询
     * @return
     * @throws Exception
     */
    @RequestMapping("/loan/findUserById")
    User findUserById(@RequestParam("id") Integer id)throws Exception;










}
