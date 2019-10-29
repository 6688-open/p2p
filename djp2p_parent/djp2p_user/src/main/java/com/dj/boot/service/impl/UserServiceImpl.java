package com.dj.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.boot.common.constant.SystemConstant;
import com.dj.boot.domain.user.entry.User;
import com.dj.boot.mapper.UserMapper;
import com.dj.boot.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    /**
     * 用户注册
     * @param user
     * @throws Exception
     */
    @Override
    public void insertUser(User user) throws Exception {
        this.save(user);
    }

    @Override
    public User findUserByUserPhone(String userPhone) throws Exception {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_phone", userPhone);
        return this.getOne(queryWrapper);
    }

    /**
     * 用户登录
     * @param userPhone  手机号码
     * @param userPassword  密码
     * @return
     * @throws Exception
     */
    @Override
    public User findUserByPhoneAndPassword(String userPhone, String userPassword) throws Exception {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_phone", userPhone);
        queryWrapper.eq("user_password", userPassword);
        return this.getOne(queryWrapper);
    }
    /**
     * 记录登录次数
     * @throws Exception
     */
    @Override
    public void updateLoginNumber(User user) throws Exception {
        this.updateById(user);
    }


    /**
     * 实名认证   将状态改为1 已认证 调用第三方
     * @param user  实体类接参数
     * @throws Exception
     */
    @Override
    public void updateUserIsAuthentication(User user) throws Exception {
        this.updateById(user);
    }


    /**
     * 开户时  验证user是否验证通过    身份证号码  真实姓名
     * @param username
     * @param idCard
     * @return
     * @throws Exception
     */
    @Override
    public User findUserByIdCardAndName(String username, String idCard) throws Exception {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("id_card", idCard);
        return this.getOne(queryWrapper);
    }

    /**
     * 查询  身份证是否存在
     * @param idCard
     * @param idCard
     * @return
     * @throws Exception
     */
    @Override
    public User findUserByIdCard(String idCard) throws Exception {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id_card", idCard);
        return this.getOne(queryWrapper);
    }


    /**
     * 查询  根据id查询
     * @return
     * @throws Exception
     */
    @Override
    public User findUserById(Integer id) throws Exception {
        return this.getById(id);
    }


    /**
     * 用户展示
     * @param pageNo  当前页
     * @return 返还数据
     * @throws Exception
     */
    @Override
    public Map<String, Object> findUserAll(Integer pageNo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Page<User> page = new Page<>(pageNo, SystemConstant.PAGESIZE_NUMBER);
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        IPage<User> iPage = this.page(page, queryWrapper);
        map.put("pageNo", pageNo);
        map.put("list", iPage.getRecords());
        map.put("totalPage", iPage.getPages());
        return map;
    }



    /**
     * 查询List集合
     */
    @Override
    public List<User> findUserList() throws Exception {
        return this.list();
    }
}
