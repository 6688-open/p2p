package com.dj.boot.controller;

import com.dj.boot.base.BaseDataApi;
import com.dj.boot.common.common.ResultModel;
import com.dj.boot.common.constant.SystemConstant;
import com.dj.boot.domain.base.BaseData;
import com.dj.boot.domain.loan.Loan;
import com.dj.boot.domain.loan.Track;
import com.dj.boot.domain.user.entry.User;
import com.dj.boot.service.LoanService;
import com.dj.boot.service.TrackService;
import com.dj.boot.user.UserApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/loan/")
@Api(value = "借款风控")
public class LoanController {
    @Autowired
    private LoanService loanService;
    @Autowired
    private BaseDataApi baseDataApi;
    @Autowired
    private UserApi userApi;
    @Autowired
    private TrackService trackService;


  /*  *//**
     * user 调用order的list
     * @return    xml  转json
     *//*
      @ApiOperation(value="查询基础数据表   借款人集合全部", notes="查询基础数据表   借款人集合全部")
        @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "token", value = "token", required = true , dataType = "String"),
            @ApiImplicitParam(name = "status", value = "status", required = true , dataType = "Long")
    })
    @PostMapping(value = "orderList", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResultModel orderList(){
        try {
            Map<String, Object> map = new HashMap<>();
            List<Orders> orderList = orderApi.findOrderList();
            map.put("orderList", orderList);
            return new ResultModel().success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }*/

    /**
     * 查询 基础数据表  借款人的集合
     * @return
     */
    @ApiOperation(value="查询基础数据表   借款人集合全部", notes="查询基础数据表   借款人集合全部")
    @PostMapping(value = "findBaseList", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResultModel findBaseList(){
        try {
            Map<String, Object> map = new HashMap<>();
            //产品名称
            List<BaseData> proList = baseDataApi.findBaseDataByParentId(SystemConstant.ONE);
            //期限
            List<BaseData> termList = baseDataApi.findBaseDataByParentId(SystemConstant.TWO);
            //还款方式
            List<BaseData> typeList = baseDataApi.findBaseDataByParentId(SystemConstant.THREE);
            //借款人集合
            List<User> userList = userApi.findUserList();
            map.put("proList", proList);
            map.put("termList", termList);
            map.put("typeList", typeList);
            map.put("userList", userList);
            return new ResultModel().success("查找成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error(e.getMessage());
        }
    }

    @ApiOperation(value="发标 新增", notes="发标 新增")
    @ApiImplicitParam(name = "loan", value = "loan实体对象", required = true , dataType = "Loan")
    @PostMapping(value = "insert", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResultModel insert(@RequestBody Loan loan){
        try {
            Map<String, Object> map = new HashMap<>();
            //断言非空判断
            Assert.notNull(loan.getPBaseName(), SystemConstant.OBJECT_IS_NULL);
            Assert.notNull(loan.getIsDisplay(), SystemConstant.OBJECT_IS_NULL);
            Assert.notNull(loan.getTargetType(), SystemConstant.OBJECT_IS_NULL);
            Assert.notNull(loan.getUserId(), SystemConstant.OBJECT_IS_NULL);
            Assert.notNull(loan.getPPrice(), SystemConstant.OBJECT_IS_NULL);
            Assert.notNull(loan.getSingleLimit(), SystemConstant.OBJECT_IS_NULL);
            Assert.notNull(loan.getInterestRate(), SystemConstant.OBJECT_IS_NULL);
            Assert.notNull(loan.getTerm(), SystemConstant.OBJECT_IS_NULL);
            Assert.notNull(loan.getRepaymentType(), SystemConstant.OBJECT_IS_NULL);
            Assert.notNull(loan.getEntryName(), SystemConstant.OBJECT_IS_NULL);
            Assert.notNull(loan.getBorrowingInstructions(), SystemConstant.OBJECT_IS_NULL);
            //20000元~1000000元。100的正整数  金额限制
            Assert.isTrue(loan.getPPrice() >= SystemConstant.TWO_NUMBER_AERO_FOUR ,"金额不能小于20000元");
            Assert.isTrue(loan.getPPrice() <= SystemConstant.NUMBER_ONE_ZERO_FIVE ,"金额限制100000以内");
            Assert.isTrue(loan.getPPrice()%SystemConstant.NUMBER_ONE_HOUNDER == SystemConstant.NUMBER_ZERO  ,"必须是100的整数");
            //利率 0.03
            loan.setInterestRate(loan.getInterestRate()*SystemConstant.NUMBER_DECIMAL);
            loan.setReleaseTime(new Date());
            //利息金额
            loan.setActualAmount(loan.getPPrice()*loan.getInterestRate()*SystemConstant.NUMBER_DECIMAL);
            loanService.insertLoan(loan);
            map.put("loan", loan);
            return new ResultModel().success("查找成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error(e.getMessage());
        }
    }

    @ApiOperation(value="初审/复审回显数据", notes="初审/复审回显数据  ")
    @ApiImplicitParam(name = "id", value = "loan   id", required = true , dataType = "Long")
    @PostMapping(value = "firstTrial", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResultModel firstTrial(@RequestParam("id") Integer id){
        try {
            Map<String, Object> map = new HashMap<>();
            //查询借款表
            Loan loan = loanService.findLoanById(id);
            //查询user 信息
            User user = userApi.findUserById(loan.getUserId());
            //查询轨迹表
            List<Track> trackList = trackService.findListByLoanId(id);
            map.put("loan", loan);
            map.put("user", user);
            map.put("trackList", trackList);
            return new ResultModel().success("查找成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error(e.getMessage());
        }

    }

    @ApiOperation(value="初审/复审", notes="初审/复审 1 初审失败 2 初审同意  3 复审 失败  4 复审完成")
    @ApiImplicitParam(name = "loan1", value = "复审loan1实体对象 ", required = true , dataType = "Loan")
    @PostMapping(value = "firstTrialAgree", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResultModel firstTrialAgree(@RequestBody Loan loan1){
        try {
            Map<String, Object> map = new HashMap<>();
            //查询借款表
            Loan loan = loanService.findLoanById(loan1.getId());
            //查询user 信息
            User user = userApi.findUserById(loan.getUserId());
            //同意  1：初审失败
            Track track = new Track();
            track.setCreateTime(new Date());
            track.setLoanId(loan1.getId());
            track.setUsername(user.getUsername());
            if(loan1.getIsAgree() == SystemConstant.ACTIVATE_ONE){
                loan.setProjectStatus(SystemConstant.ACTIVATE_ONE);
                track.setRemark("初审失败");
            }
            if(loan1.getIsAgree() == SystemConstant.TWO){// 2：复审中
                loan.setProjectStatus(SystemConstant.TWO);
                track.setRemark("复审中");
            }

            //复审
            if(loan1.getIsAgree() == SystemConstant.THREE || loan1.getIsAgree() == SystemConstant.FOUR){
                //非空判断
                Assert.notNull(loan1.getBeforeDefaultPricePercentage(), SystemConstant.OBJECT_IS_NULL);
                Assert.notNull(loan1.getDefaultPricePercentage(), SystemConstant.OBJECT_IS_NULL);
                Assert.notNull(loan1.getAfterDefaultPricePercentage(), SystemConstant.OBJECT_IS_NULL);
                Assert.notNull(loan1.getLoanHandlingFee(), SystemConstant.OBJECT_IS_NULL);
                Assert.notNull(loan1.getFundraisingNumber(), SystemConstant.OBJECT_IS_NULL);
                Assert.notNull(loan1.getWindControlProposal(), SystemConstant.OBJECT_IS_NULL);
                Assert.notNull(loan1.getLoanContract(), SystemConstant.OBJECT_IS_NULL);
                //提前还款违约金百分比
                loan.setBeforeDefaultPricePercentage(loan1.getBeforeDefaultPricePercentage()*SystemConstant.NUMBER_DECIMAL);
                //违约百分比
                loan.setDefaultPricePercentage(loan1.getDefaultPricePercentage()*SystemConstant.NUMBER_DECIMAL);
                //逾期罚息百分比
                loan.setAfterDefaultPricePercentage(loan1.getAfterDefaultPricePercentage()*SystemConstant.NUMBER_DECIMAL);
                //借款存续期手续费百分比
                loan.setLoanHandlingFee(loan1.getLoanHandlingFee()*SystemConstant.NUMBER_DECIMAL);
                loan.setSellingTime(new Date());
                loan.setFundraisingNumber(loan1.getFundraisingNumber());
                loan.setWindControlProposal(loan1.getWindControlProposal());
                loan.setLoanContract(loan1.getLoanContract());
            }
            if(loan1.getIsAgree() == SystemConstant.NUMBER_THREE){// 3：复审失败
                loan.setProjectStatus(SystemConstant.NUMBER_THREE);
                track.setRemark("复审失败");
            }
            if(loan1.getIsAgree() == SystemConstant.FOUR){// 4：完成
                loan.setProjectStatus(SystemConstant.FOUR);
                track.setRemark("完成");
            }
            //修改loan状态   添加轨迹表哦
            loanService.updateLoanById(loan, track);
            map.put("loan", loan);
            map.put("user", user);
            map.put("track", track);
            return new ResultModel().success("查找成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error(e.getMessage());
        }

    }


    @ApiOperation(value="type 1  风控    2 理财项目", notes=" type 1  风控    2 理财项目 ")
    @ApiImplicitParam(name = "type", value = "type", required = true , dataType = "Long")
    @PostMapping(value = "List", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResultModel List(@RequestParam("type") Integer type){
        //type 1  展示所有  2 展示完成的   类型4
        try {
            Map<String, Object> map = new HashMap<>();
            List<Loan> loanList = loanService.findList(type);
            map.put("loanList", loanList);
            return new ResultModel().success("查找成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error(e.getMessage());
        }
    }












}
