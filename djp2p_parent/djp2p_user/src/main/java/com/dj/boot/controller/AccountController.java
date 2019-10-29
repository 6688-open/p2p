package com.dj.boot.controller;

import com.dj.boot.common.common.ResultModel;
import com.dj.boot.common.constant.SystemConstant;
import com.dj.boot.domain.account.entry.Account;
import com.dj.boot.domain.user.entry.User;
import com.dj.boot.redis.RedisService;
import com.dj.boot.service.AccountService;
import com.dj.boot.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/account/")
@Api(value = "开户")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @ApiOperation(value="开户", notes="p2p用户开户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "p2p开户实体", required = true, dataType = "Account"),
            @ApiImplicitParam(name = "token", value = "token", required = true , dataType = "String")
    })
    @PostMapping("account")
    public ResultModel account(@RequestHeader("token") String token, @RequestBody Account account){

        //断言非空判断
        Assert.notNull(account.getClientUserName(), SystemConstant.OBJECT_IS_NULL);
        Assert.notNull(account.getIdCard(), SystemConstant.OBJECT_IS_NULL);
        Assert.notNull(account.getBankCard(), SystemConstant.OBJECT_IS_NULL);
        Assert.notNull(account.getAccountType(), SystemConstant.OBJECT_IS_NULL);
        Assert.notNull(account.getPhone(), SystemConstant.OBJECT_IS_NULL);
        Assert.notNull(account.getPayPassword(), SystemConstant.OBJECT_IS_NULL);
        Assert.notNull(account.getPaySurePassword(), SystemConstant.OBJECT_IS_NULL);
        //判断密码是否一致
        Assert.isTrue(account.getPayPassword().equals(account.getPaySurePassword()),"密码不一致");
        HashMap<String, Object> map = new HashMap<>();
        //保存account  并修改user用户的状态  开户2   银行卡号保存
        try {
            //判断传来的身份证号码 和名字是否一致
            User user = userService.findUserByIdCardAndName(account.getClientUserName(), account.getIdCard());
            Assert.notNull(user, "认证时真实姓名 身份证号  不一致");
            //判断开户表里的去重
            Account account1 = accountService.findAccountByIdCard(account.getIdCard());
            Assert.isNull(account1, "该用户已经开户了");
            //保存
            accountService.insertAccount(account);
            map.put("account", account);
            map.put("token", token);
            map.put("user", userService.findUserByIdCardAndName(account.getClientUserName(), account.getIdCard()));
            return new ResultModel().success("success",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }




}
