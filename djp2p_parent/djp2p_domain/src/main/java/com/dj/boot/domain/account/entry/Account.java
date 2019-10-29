package com.dj.boot.domain.account.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("p2p_account")
public class Account implements Serializable  {
    @TableId(type = IdType.AUTO)
    /**
     * 开户人ID
     */
    private Integer id;
    /**
     * 客户名称
     */
    private String clientUserName;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     *银行卡号
     */
    private String bankCard;
    /**
     * 账户类型：0投资人, 1 借款人
     */
    private String accountType;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 支付密码
     */
    private String payPassword;
    /**
     * 确认支付密码
     */
    @TableField(exist = false)
    private String paySurePassword;



}
