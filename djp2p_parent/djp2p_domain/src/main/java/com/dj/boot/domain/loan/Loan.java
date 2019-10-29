package com.dj.boot.domain.loan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("p2p_loan")
public class Loan implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 产品 基础数据表的id
     */
    private Integer pBaseName;

    /**
     * 是否显示0显示  1显示
     */
    private Integer isDisplay;

    /**
     * 标的类型：0个人标 1企业标
     */
    private String targetType;

    /**
     * 关联借款人
     */
    private Integer userId;

    /**
     * 金额
     */
    private Double pPrice;

    /**
     * 单人1限额， 2不限额
     */
    private Integer singleLimit;

    /**
     * 年利率
     */
    private Double interestRate;

    /**
     * 基础数据表中的id
     */
    private Integer term;

    /**
     * 还款方式基础数据表中的id
     */
    private Integer repaymentType;

    /**
     * 项目名称
     */
    private String entryName;

    /**
     * 借款说明
     */
    private String borrowingInstructions;

    /**
     * 利息金额
     */
    private Double actualAmount;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date releaseTime;

    /**
     * 发售时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sellingTime;

    /**
     * 筹款进度
     */
    private Integer speedOfProgress;

    /**
     * 项目状态0初审中 1初审失败 2复审中3复审失败 4 借款中 5待签约 6 待 放款7还款中8已完成9流标
     */
    private Integer projectStatus;

    /**
     * 筹款时间
     */
    private Integer fundraisingNumber;

    /**
     * 0：借款合同 1 四方借款合同
     */
    private Integer loanContract;

    /**
     * 风控建议
     */
    private String windControlProposal;

    /**
     * 违约百分比
     */
    private Double defaultPricePercentage;
    /**
     * 提前还款违约金百分比
     */
    private Double beforeDefaultPricePercentage;
    /**
     * 逾期罚息百分比
     */
    private Double afterDefaultPricePercentage;
    /**
     * 借款存续期手续费百分比
     */
    private Double loanHandlingFee;
    /**
     * 借款存续期手续费类型
     */
    private Integer loanHandlingFeeType;
    /**
     * 已经筹款金额
     */
    private double havePrice;
    /**
     * 加入次数
     */
    private Integer numberTime;


    /**
     * 1 初审失败 2 初审同意  3 复审 失败  4 复审完成
     */
    @TableField(exist = false)
    private Integer isAgree;

}
