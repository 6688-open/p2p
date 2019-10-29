package com.dj.boot.domain.loan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("exp")
public class Exp implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 标 id
     */
    private Integer loanId;
    /**
     * 投资人id
     */
    private Integer userId;
    /**
     *期数
     */
    private Integer term;
    /**
     *本金 利息
     */
    private double totalMoney;
    /**
     * 本金
     */
    private double money;
    /**
     * 利息
     */
    private double interestMoney;
    /**
     * 是否还款 1 未还  2 已还
     */
    private Integer isPay;
    /**
     * 到期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date termTime;
    /**
     * 还款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date huankuanTime;

}
