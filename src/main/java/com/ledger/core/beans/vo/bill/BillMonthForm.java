package com.ledger.core.beans.vo.bill;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author pang
 * @version V1.0
 * @ClassName: BillMonthForm
 * @Package com.ledger.core.beans.vo.bill
 * @description: 月账单
 * @date 2020/1/9 9:49
 */
@Data
@ToString
@EqualsAndHashCode
public class BillMonthForm {
    /**
     * 当前月
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM")
    private Date month;
    /**
     * 月账单
     */
    private List<BillForm> dayBill;
    /**
     * 消费总和（+收入-支出）
     */
    private Double totalPrice;
}
