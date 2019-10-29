package com.dj.boot.controller;

import com.dj.boot.common.common.ResultModel;
import com.dj.boot.common.constant.SystemConstant;
import com.dj.boot.common.util.UUIDUtil;
import com.dj.boot.domain.user.entry.User;
import com.dj.boot.redis.RedisService;
import com.dj.boot.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/")
@Api(value = "p2p用户")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    /**
     * 用户注册  默认 未认证  无角色
     * @return
     */
    @ApiOperation(value="p2p用户注册", notes="p2p用户注册")
    @ApiImplicitParam(name = "user", value = "p2p用户实体", required = true, dataType = "User")
    @PostMapping("register")
    public ResultModel register(@RequestBody User user){
        //断言非空判断
        Assert.notNull(user.getUserPhone(), SystemConstant.OBJECT_IS_NULL);
        Assert.notNull(user.getUserPassword(), SystemConstant.OBJECT_IS_NULL);
        Assert.notNull(user.getUserSex(), SystemConstant.OBJECT_IS_NULL);
        Assert.notNull(user.getUserAge(), SystemConstant.OBJECT_IS_NULL);
        Assert.notNull(user.getUserMarriage(), SystemConstant.OBJECT_IS_NULL);
        Assert.notNull(user.getUserEducation(), SystemConstant.OBJECT_IS_NULL);
        Assert.notNull(user.getUserWorkingLife(), SystemConstant.OBJECT_IS_NULL);
        Assert.notNull(user.getUserHouseProperty(), SystemConstant.OBJECT_IS_NULL);
        Assert.notNull(user.getUserYearsIncome(), SystemConstant.OBJECT_IS_NULL);
        Assert.notNull(user.getUserAssetsValuation(), SystemConstant.OBJECT_IS_NULL);
        Assert.notNull(user.getUserCarYield(), SystemConstant.OBJECT_IS_NULL);
        Assert.isTrue(user.getUserSurePassword().equals(user.getUserPassword()),"密码不一致");
        try {
            HashMap<String, Object> map = new HashMap<>();
            //手机去重
            User user1 = userService.findUserByUserPhone(user.getUserPhone());
            Assert.isNull(user1, "手机号已存在");
            //生成token
            String token = UUIDUtil.code();
            //存redis
            redisService.set(token,user);
            System.out.println(token);
            //保存
            userService.insertUser(user);
            map.put("user",user);
            map.put("token",token);
            return new ResultModel().success("success",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }

    }

    /**
     * 用户登录
     * @return
     */
    @ApiOperation(value="p2p用户登录", notes="p2p用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPhone", value = "用户手机号码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userPassword", value = "用户密码", required = true , dataType = "String")
    })
    @PostMapping("login")
    public ResultModel login(@RequestParam("userPhone") String userPhone, @RequestParam("userPassword") String userPassword){
        try {
            HashMap<String, Object> map = new HashMap<>();
            //断言非空判断
            Assert.notNull(userPhone, SystemConstant.OBJECT_IS_NULL);
            Assert.notNull(userPassword, SystemConstant.OBJECT_IS_NULL);
            User user = userService.findUserByPhoneAndPassword(userPhone, userPassword);
            Assert.notNull(user, "该用户不存在");
            //判断是否锁定
            Assert.isTrue(user.getStatus() == SystemConstant.ACTIVATE_ONE,"该用户已锁定");
            //生成token
            String token = UUIDUtil.code();
            //存redis
            redisService.set(token, user);
            System.out.println(token);
            //保存登录次数  +1
            user.setLoginNumber(user.getLoginNumber()+SystemConstant.ACTIVATE_ONE);
            user.setLoginTime(new Date());
            userService.updateLoginNumber(user);

            map.put("user",user);
            map.put("token",token);
            //未认证
          /*  if(user.getUserIsAuthentication() == 0){
                return new ResultModel().success(100,"该用户未实名认证",user);
            }*/
            return new ResultModel().success("登录成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }

    /**
     * 实名认证
     * @param username  用户名
     * @param IDcard  身份证号
     * @return
     */
    @ApiOperation(value="p2p用户实名认证", notes="p2p用户实名认证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "IDcard", value = "身份证号码", required = true , dataType = "String"),
            @ApiImplicitParam(name = "token", value = "token", required = true , dataType = "String")
    })
    @PostMapping("authentication")
    public ResultModel authentication(@RequestHeader("token") String token, @RequestParam("username") String username, @RequestParam("IDcard") String IDcard){
        try {
            HashMap<String, Object> map = new HashMap<>();
            //判断改身份证是否存在
            User user1 = userService.findUserByIdCard(IDcard);
            Assert.isNull(user1, "身份证已存在");

            User user = (User)redisService.get(token);
            user.setUsername(username);
            user.setIdCard(IDcard);
            user.setUserIsAuthentication(SystemConstant.ACTIVATE_ONE);
            //修改 保存
            userService.updateUserIsAuthentication(user);
            map.put("user",user);
            map.put("token",token);
            return new ResultModel().success("该用户已实名认证",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }

    /**
     * 用户展示
     * @return
     */
    @ApiOperation(value="p2p用户列表", notes="p2p用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "token", value = "token", required = true , dataType = "String")
    })
    @PostMapping("list")
    public ResultModel list(@RequestHeader("token") String token, @RequestParam("pageNo") Integer pageNo){
        try {
            Map<String, Object> map = userService.findUserAll(pageNo);
            map.put("token", token);
            return new ResultModel().success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }


    /**
     * 修改用户状态  将未锁定改为已锁定   已锁定改为未锁定
     */
    @ApiOperation(value="修改用户状态  未锁定 /已锁定", notes="修改用户状态  未锁定 /已锁定")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "token", value = "token", required = true , dataType = "String"),
            @ApiImplicitParam(name = "status", value = "status", required = true , dataType = "Long")
    })
    @PostMapping("updateStatus")
    public ResultModel updateStatus(@RequestHeader("token") String token,@RequestParam("status") Integer status, @RequestParam("id") Integer id){
        try {
            HashMap<String, Object> map = new HashMap<>();
            User user = new User();
            user.setId(id).setStatus(status);
            //根据id 修改状态 将未锁定改为已锁定   已锁定改为未锁定  前台传来将要更改的状态
            userService.updateUserIsAuthentication(user);
            User user1 = userService.findUserById(id);
            map.put("token", token);
            map.put("user", user1);
            return new ResultModel().success(map);
        } catch (Exception e) {
            return new ResultModel().error("异常"+e.getMessage());
        }
    }



}
