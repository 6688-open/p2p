package com.dj.boot.controller;

import com.dj.boot.common.common.ResultModel;
import com.dj.boot.common.constant.SystemConstant;
import com.dj.boot.domain.account.entry.Account;
import com.dj.boot.domain.money.Money;
import com.dj.boot.domain.user.entry.User;
import com.dj.boot.redis.RedisService;
import com.dj.boot.service.AccountService;
import com.dj.boot.service.MoneyService;
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
@RequestMapping("/money/")
@Api(value = "充值业务")
public class MoneyController {

    @Autowired
    private MoneyService moneyService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    /**
     * 查询余额
     * @return
     */
    @ApiOperation(value="去查询总余额", notes="去查询总余额")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String")
    @PostMapping("getBalanceMoney")
    public ResultModel getBalanceMoney(@RequestHeader("token") String token){
        try {
            HashMap<String, Object> map = new HashMap<>();
            User user = (User)redisService.get(token);
            Money money = moneyService.findMoneyByIdCard(user.getIdCard());
            //回显信息 预留手机号码  Account\
          /*  Account account = accountService.findAccountByIdCard(user.getIdCard());
            map.put("account", account);*/
            map.put("token", token);
            map.put("money", money);
            return new ResultModel().success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }

    /**
     * 充值
     * @return
     */
    @ApiOperation(value="充值", notes="充值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String"),
            @ApiImplicitParam(name = "money", value = "充值钱数", required = true , dataType = "Long")
    })
    @PostMapping("insertMoney")
    public ResultModel insertMoney(@RequestHeader("token") String token, @RequestParam("money") double money){
        try {
            HashMap<String, Object> map = new HashMap<>();
            User user = (User)redisService.get(token);
           /* if(money <10){
                return new ResultModel().error("单笔不能低于10元");
            }*/
            Assert.isTrue(money >= 10,"单笔不能低于10元");
            Account account = accountService.findAccountByIdCard(user.getIdCard());
            map.put("account", account);
            map.put("money", money);
            map.put("token", token);
            return new ResultModel().success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }






    /**
     * 提现
     * @return
     */
    @ApiOperation(value="提现", notes="提现")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String")
    @PostMapping("getMoney")
    public ResultModel getMoney(@RequestHeader("token") String token){
        try {
            HashMap<String, Object> map = new HashMap<>();
            User user = (User)redisService.get(token);
            User user1 = userService.findUserById(user.getId());
            Account account = accountService.findAccountByIdCard(user1.getIdCard());


            Money money1 = moneyService.findMoneyByIdCard(user.getIdCard());
            map.put("account", account);
            map.put("money", money1);
            map.put("token", token);
            return new ResultModel().success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }


    /**
     * 支付密码
     * @return
     */
    @ApiOperation(value="支付密码", notes="支付密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String"),
            @ApiImplicitParam(name = "money", value = "钱数", required = true , dataType = "Long"),
            @ApiImplicitParam(name = "status", value = "提现方式 1 立即提现 2 普通提现", required = true , dataType = "Long"),
            @ApiImplicitParam(name = "type", value = "支付 1 充值  2 提现", required = true , dataType = "Long"),
            @ApiImplicitParam(name = "payPassword", value = "支付密码", required = true , dataType = "String")
    })
    @PostMapping("getMoneyPay")
    public ResultModel getMoneyPay(@RequestHeader("token") String token,@RequestParam("type") Integer type, @RequestParam("status") Integer status, @RequestParam("payPassword") String payPassword, @RequestParam("money") double money){
        try {
            HashMap<String, Object> map = new HashMap<>();
            User user = (User)redisService.get(token);
            User user1 = userService.findUserById(user.getId());
            Account account = accountService.findAccountByIdCard(user1.getIdCard());
            Assert.isTrue(account.getPayPassword().equals(payPassword),"支付密码错误");
            Money money1 = moneyService.findMoneyByIdCard(user.getIdCard());
            //充值
            if(type == SystemConstant.TYPE_NUMBER){
                money1.setBalanceMoney(money1.getBalanceMoney()+money);
            }
            if(type == SystemConstant.TWO){
                Assert.isTrue(money1.getBalanceMoney() >=  money,"余额不足");
                money1.setBalanceMoney(money1.getBalanceMoney()-money);
            }
            moneyService.insertOrUpdate(money1);
            map.put("account", account);
            map.put("money", money1);
            map.put("token", token);
            return new ResultModel().success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }







}
