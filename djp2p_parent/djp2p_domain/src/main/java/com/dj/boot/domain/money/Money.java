package com.dj.boot.domain.money;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("p2p_money")
public class Money implements Serializable {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 充值金额
     */
    private double balanceMoney;
    /**
     *
     */
    private String idCard;
    /**
     * 冻结金额
     */
    private double unuseMoney;
    /**
     * 待还金额
     */
    private double huanMoney;
    /**
     * 代收金额
     */
    private double shouMoney;


}
