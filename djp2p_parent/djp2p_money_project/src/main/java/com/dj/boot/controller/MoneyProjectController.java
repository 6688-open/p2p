package com.dj.boot.controller;

import com.dj.boot.common.common.ResultModel;
import com.dj.boot.common.constant.SystemConstant;
import com.dj.boot.common.util.UUIDUtil;
import com.dj.boot.domain.account.entry.Account;
import com.dj.boot.domain.loan.Exp;
import com.dj.boot.domain.loan.Loan;
import com.dj.boot.domain.money.Money;
import com.dj.boot.domain.user.entry.User;
import com.dj.boot.redis.RedisService;
import com.dj.boot.service.ExpService;
import com.dj.boot.service.MoneyProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/money/")
@Api(value = "理财项目")
public class MoneyProjectController {
    @Autowired
    private MoneyProjectService moneyProjectService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ExpService expService;



    @ApiOperation(value="我的借款 我的出借    以及展示理财项目 只展示状态4 5,6,7,8 借款中", notes="我的借款    以及展示理财项目 只展示状态4 5,6,7,8 借款中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型type1我的借款 userid  2  理财项目 只展示状态4 5,6,7,8 借款中  3 我的出借（投资人） 只展示状态4    ", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "token", value = "token", required = true , dataType = "String")
    })
    @PostMapping("list")
    public ResultModel list(@RequestHeader("token") String token, @RequestParam("type") Integer type){
        try {
            HashMap<String, Object> map = new HashMap<>();
            User user = (User)redisService.get(token);
            List<Loan> loanList = moneyProjectService.findLoanList(type, user.getId());
            map.put("user", user);
            map.put("loanList", loanList);
            map.put("token", token);
            return new ResultModel().success("该用户已实名认证",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }

    @ApiOperation(value="去购买 回显数据", notes="去购买 回显数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "根据id查询", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "token", value = "token", required = true , dataType = "String")
    })
    @PostMapping("toBuyNow")
    public ResultModel toBuyNow(@RequestHeader("token") String token, @RequestHeader("id") String id ){
        try {
            HashMap<String, Object> map = new HashMap<>();
             //投资人登陆人 购买
            User user = (User)redisService.get(token);
            //回显 投资人的资产
            Money money = moneyProjectService.findMoneyByIdCard(user.getIdCard());
            map.put("user", user);
            map.put("money", money);
            map.put("id", id);
            return new ResultModel().success("回显成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }

    @ApiOperation(value="投资人购买", notes="投资人购买")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "根据标id查询", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "token", value = "token", required = true , dataType = "String"),
            @ApiImplicitParam(name = "money", value = "购买金额", required = true , dataType = "String"),
            @ApiImplicitParam(name = "payPassword", value = "支付密码", required = true , dataType = "String")
    })
    @PostMapping("buy")
    public ResultModel buy(@RequestHeader("token") String token, @RequestHeader("money") double money, @RequestHeader("id") Integer id, @RequestHeader("payPassword") String payPassword){
        try {
            HashMap<String, Object> map = new HashMap<>();
            //投资人登陆人 购买
            User user = (User)redisService.get(token);
            //回显 投资人的资产
            Money money1 = moneyProjectService.findMoneyByIdCard(user.getIdCard());
            //验证支付密码
            Account account = moneyProjectService.findAccountByIdCard(user.getIdCard());



            //获取 标的数据 根据id
            Loan loan = moneyProjectService.findLoanById(id);

            if(loan.getPPrice() != money ){
                return new ResultModel().error("需要支付"+loan.getPPrice());
            }
            if(money1.getBalanceMoney() < money ){
                return new ResultModel().error("余额不足  充值 ");
            }
            if(!account.getPayPassword().equals(payPassword) ){
                return new ResultModel().error("支付密码错误");
            }


            //标已满
            //有效时间设置
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, -loan.getFundraisingNumber());// 今天+2 分钟
            Date date = c.getTime();





            //在筹款时间内  全部已经筹完    贷款放款 5   投资人记录账单   加入次数+1 投资人 余额减少 --》 资金冻结
            if(loan.getSellingTime().getTime() > date.getTime()  ){
                //修改状态   8 流标
                //投资人 余额减少 --》 资金冻结
                money1.setBalanceMoney(money1.getBalanceMoney()-money);
                money1.setUnuseMoney(money1.getUnuseMoney()+money);
               // money1.setShouMoney(money1.getShouMoney()+ money + money*loan.getInterestRate() );
                moneyProjectService.updateMoneyByIdCard(money1);
                //添加账单 默认 4期
                Integer a = 4;
                Calendar d = Calendar.getInstance();
                List<Exp> list = new ArrayList<>();
                for (Integer i = 0; i < a; i++) {
                    d.add(Calendar.DAY_OF_MONTH, (i+1)*30);// 今天+2 分钟
                    //到期时间
                    Date termTime = c.getTime();
                    Exp exp = new Exp();
                    exp.setUserId(user.getId());
                    exp.setLoanId(loan.getId());
                    exp.setTerm(i+1);
                    exp.setMoney(money/a);
                    exp.setInterestMoney((money*loan.getInterestRate())/a);
                    exp.setTotalMoney((money+money*loan.getInterestRate())/a);
                    exp.setTermTime(termTime);
                    list.add(exp);
                }
                expService.insertExp(list);
                //修改标状态 待签约
                loan.setProjectStatus(5);
                loan.setNumberTime(1);
                moneyProjectService.updateLoanById(loan);
                map.put("user", user);
                map.put("money", money1);
                map.put("loan", loan);
                map.put("token", token);
                return new ResultModel().success("购买成功", map);
            }
            //标未满 没有筹完 流标   发售时间 + 筹款天数   当前时间
            if(loan.getSellingTime().getTime() < date.getTime() ){
                //修改状态   8 流标
                loan.setProjectStatus(8);
                moneyProjectService.updateLoanById(loan);
                map.put("token", token);
                return new ResultModel().success("流标",map);
            }

            return new ResultModel().success("购买成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }


    @ApiOperation(value="签约", notes="签约")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "签约根据标id查询", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "token", value = "token", required = true , dataType = "String")
    })
    @PostMapping("sureOrder")
    public ResultModel sureOrder(@RequestHeader("token") String token, @RequestHeader("id") Integer id){
        try {
            HashMap<String, Object> map = new HashMap<>();
            //获取 标的数据 根据id   //贷放款
            Loan loan = moneyProjectService.findLoanById(id);
            loan.setProjectStatus(6);
            moneyProjectService.updateLoanById(loan);
            map.put("loan", loan);
            map.put("token", token);
            return new ResultModel().success("签约成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }




    @ApiOperation(value="放款", notes="放款")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "放款根据标id查询", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "token", value = "token", required = true , dataType = "String")
    })
    @PostMapping("makeLoan")
    public ResultModel makeLoan(@RequestHeader("token") String token, @RequestHeader("id") Integer id){
        try {
            HashMap<String, Object> map = new HashMap<>();
            //获取 标的数据 根据id   //贷放款
            Loan loan = moneyProjectService.findLoanById(id);
            //根据id 查询user 借款人信息
            User user = moneyProjectService.findUserById(loan.getUserId());
            //查询借款人的账户信息
            Money money = moneyProjectService.findMoneyByIdCard(user.getIdCard());
            money.setBalanceMoney(money.getBalanceMoney()+loan.getPPrice());
            money.setHuanMoney(money.getHuanMoney()+loan.getPPrice()+loan.getPPrice()*loan.getInterestRate());
            //修改借款人的钱数  加入到余额  待还金额+
            moneyProjectService.updateMoneyByIdCard(money);
            //投资人 冻结金额 减去 改为代收金额
            //投资人登陆人 购买
            User user1 = (User)redisService.get(token);
            //查询投资人的账户信息
            Money money1 = moneyProjectService.findMoneyByIdCard(user1.getIdCard());
            money1.setUnuseMoney(money1.getUnuseMoney()-loan.getPPrice());
            money1.setShouMoney(money1.getShouMoney()+loan.getPPrice()+loan.getPPrice()*loan.getInterestRate());
            moneyProjectService.updateMoneyByIdCard(money1);


            moneyProjectService.updateMoneyByIdCard(money1);
            //修改状态 为 还款中  7
            loan.setProjectStatus(7);
            moneyProjectService.updateLoanById(loan);
            map.put("loan", loan);
            map.put("token", token);
            return new ResultModel().success("放款成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }


    @ApiOperation(value="借款人 投资人 查询账单", notes="借款人 投资人 查询账单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "放款根据标id查询", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "token", value = "token", required = true , dataType = "String")
    })
    @PostMapping("findLoan")
    public ResultModel findLoan(@RequestHeader("token") String token, @RequestHeader("id") Integer id){
        try {
            HashMap<String, Object> map = new HashMap<>();
            List<Exp> expList = expService.findListByLoanId(id);
            map.put("expList", expList);
            map.put("token", token);
            return new ResultModel().success("查询账单 成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }

    @ApiOperation(value="查询账单id 进行还款", notes="查询账单id 进行还款")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loanId", value = "标id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "expId", value = "查询账单id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "token", value = "token", required = true , dataType = "String")
    })
    @PostMapping("repayment")
    public ResultModel repayment(@RequestHeader("token") String token, @RequestHeader("loanId") Integer loanId, @RequestHeader("expId") Integer expId){
        try {
            HashMap<String, Object> map = new HashMap<>();
            //根据expid 查询
            Exp exp = expService.findExpById(expId);

            //redis 取 借款人id
            User user = (User)redisService.get(token);
            //回显 借款人的资产
            //修改借款人的资产 余额 待还 减去 要还的钱数
            Money money = moneyProjectService.findMoneyByIdCard(user.getIdCard());
            money.setBalanceMoney(money.getBalanceMoney()-exp.getTotalMoney());//余额减去
            money.setHuanMoney(money.getHuanMoney()-exp.getTotalMoney());//待还金额减去
            moneyProjectService.updateMoneyByIdCard(money);


            //修改投资人的账户钱数  exp 获取投资人的id
            User user1 = moneyProjectService.findUserById(exp.getUserId());
            //回显 投资人的资产 代收金额 减去   余额增加
            Money money1 = moneyProjectService.findMoneyByIdCard(user1.getIdCard());
            money1.setShouMoney(money1.getShouMoney() - exp.getTotalMoney());
            money1.setBalanceMoney(money1.getBalanceMoney()+ exp.getTotalMoney());
            moneyProjectService.updateMoneyByIdCard(money1);

            //修改exp 状态已还 ispay 2  还款时间
            exp.setIsPay(2);
            exp.setHuankuanTime(new Date());
            expService.updateExpById(exp);

            //查询是否有未还的账单 已还完 修改已完成
            List<Exp> expList = expService.findListByLoanId(loanId);
            String isTure = "";
            //有1 的存在 说明 没有还完  isTure 就不会为空   如果isTure == null  说明已全部还完 就修改状态已完成  8
            for (Exp exp1 : expList) {
              if(exp1.getIsPay() == 1){
                  isTure += "no";
              }
            }
            if(isTure == ""){
                Loan loan = moneyProjectService.findLoanById(loanId);
                loan.setProjectStatus(8);
                moneyProjectService.updateLoanById(loan);
                map.put("exp", exp);
                return new ResultModel().success("账单已全部还完",map);
            }


            map.put("exp", exp);
            return new ResultModel().success("还款 成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }












}
