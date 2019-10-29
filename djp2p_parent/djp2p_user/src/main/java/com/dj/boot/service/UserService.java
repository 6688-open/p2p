package com.dj.boot.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.boot.domain.user.entry.User;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param user
     * @throws Exception
     */
    void insertUser(User user) throws Exception;

    /**
     * 手机号码去重
     * @param userPhone
     * @return
     * @throws Exception
     */
    User findUserByUserPhone(String userPhone)throws Exception;

    /**
     * 用户登录
     * @param userPhone  手机号码
     * @param userPassword  密码
     * @return
     * @throws Exception
     */
    User findUserByPhoneAndPassword(String userPhone, String userPassword) throws Exception;

    /**
     * 记录登录次数
     * @throws Exception
     */
    void updateLoginNumber(User user) throws Exception;

    /**
     * 实名认证   将状态改为1 已认证 调用第三方
     * @param user  实体类接参数
     * @throws Exception
     */
    void updateUserIsAuthentication(User user)throws Exception;


    /**
     * 开户时  验证user是否验证通过    身份证号码  真实姓名
     * @param username
     * @param idCard
     * @return
     * @throws Exception
     */
    User findUserByIdCardAndName(String username, String idCard)throws Exception;
    /**
     * 查询  身份证是否存在
     * @param idCard
     * @param idCard
     * @return
     * @throws Exception
     */
    User findUserByIdCard(String idCard)throws Exception;
    /**
     * 查询  根据id查询
     * @return
     * @throws Exception
     */
    User findUserById(Integer id)throws Exception;


    /**
     * 用户展示
     * @param pageNo  当前页
     * @return 返还数据
     * @throws Exception
     */
    Map<String, Object> findUserAll(Integer pageNo)throws Exception;

    /**
     * 查询List集合
     */
    List<User> findUserList()throws Exception;


}
