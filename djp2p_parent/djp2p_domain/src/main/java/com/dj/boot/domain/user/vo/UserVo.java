package com.dj.boot.domain.user.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserVo implements Serializable  {
    @TableId(type = IdType.AUTO)
    /**
     * 用户ID
     */
    private Integer id;
    /**
     * 手机号码
     */
    private String userPhone;
    /**
     * 密码
     */
    private String userPassword;
    /**
     * 性别  0:男 1:女 2:保密
     */
    private Integer userSex;
    /**
     * 年龄
     */
    private Integer userAge;

    /**
     * 学历 1小学 2 初中 3 高中 4 专科 5 本科6 研究生及以上
     */
    private String userEducation;
    /**
     * 婚姻状态 1 未婚 2 已婚
     */
    private Integer userMarriage;
    /**
     * 工作年限 1    1-3年 2      3-5年 3     5-8年 4      8年以上
     */
    private Integer userWorkingLife;
    /**
     * 房产  1 全款买房 2 贷款买房 3租房 4 无
     */
    private Integer userHouseProperty;
    /**
     * 年收入 1 1-5万 2 5-10万 3 10-20万 4 20-30万 5 30-50万  6 50-100万  7 100万以上
     */
    private Integer userYearsIncome;
    /**
     * 资产估值
     */
    private String userAssetsValuation;
    /**
     * 车产 1 全款购车  2  贷款购车 3 无
     */
    private Integer userCarYield;
    /**
     * 是否是认证后的状态 0:未认证  1已认证
     */
    private Integer userIsAuthentication;
    /**
     *  用户角色为：1 借款人，2 投资人、 3 风控专员、 4 风控经理、 5 风控总监
     */
    private Integer userRole;



    /**
     * token
     */
    @TableField(exist = false)
    private String token;



}
