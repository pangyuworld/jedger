package com.ledger.core.beans.vo.budget;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * @author pang
 * @version V1.0
 * @ClassName: Budget
 * @Package com.ledger.core.beans.po
 * @description: 预算表单
 * @date 2020/1/9 13:31
 */
@Data
@ToString
@EqualsAndHashCode
public class BudgetForm {
    /**
     * 预算ID
     */
    private Long budgetId;
    /**
     * 预算时间（预算所处的月）
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd")
    private Date budgetTime;
    /**
     * 预算金额
     */
    private Double budgetPrice;
    /**
     * 设置预算时候的备注
     */
    private String budgetRemark;
}
