package com.ledger.core.beans.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * @author pang
 * @version V1.0
 * @ClassName: Bill
 * @Package com.ledger.core.beans.po
 * @description: 账单表
 * @date 2020/1/8 21:43
 */
@Data
@ToString
@EqualsAndHashCode
public class Bill {
    /**
     * 账单ID
     */
    private Long billId;
    /**
     * 记录账单的时间
     */
    private Date billTime;
    /**
     * 记录账单的金额
     */
    private Double billPrice;
    /**
     * 记录账单时候的备注
     */
    private String billRemark;
    /**
     * 记录账单的用户
     */
    private Long userId;
    /**
     * 账单品类
     */
    private Long categoryId;
}
