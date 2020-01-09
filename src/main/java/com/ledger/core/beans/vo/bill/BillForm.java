package com.ledger.core.beans.vo.bill;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * @author pang
 * @version V1.0
 * @ClassName: BillForm
 * @Package com.ledger.core.beans.vo.bill
 * @description: 账单表单
 * @date 2020/1/9 9:01
 */
@Data
@ToString
@EqualsAndHashCode
public class BillForm {
    /**
     * 记录账单的时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd HH:mm:ss")
    private Date billTime;
    /**
     * 记录账单的金额（总金额）
     */
    private Double billPrice;
}
