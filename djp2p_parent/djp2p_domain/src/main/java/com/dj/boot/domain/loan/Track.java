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
@TableName("track")
public class Track implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 借款表id
     */
    private Integer loanId;
    /**
     * 借款人
     */
    private  String username;
    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 备注
     */
    private String remark;
}
