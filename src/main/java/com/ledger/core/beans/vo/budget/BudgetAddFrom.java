package com.ledger.core.beans.vo.budget;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author pang
 * @version V1.0
 * @ClassName: BudgetAddFrom
 * @Package com.ledger.core.beans.vo.budget
 * @description: 添加预算的表单
 * @date 2020/1/9 13:48
 */
@Data
@ToString
@EqualsAndHashCode
public class BudgetAddFrom {
    /**
     * 预算时间（预算所处的月）
     */
    @NotNull(message = "预算时间不能为空")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd")
    private Date budgetTime;
    /**
     * 预算金额
     */
    @Min(value = 0, message = "预算金额不能是负数")
    @NotNull(message = "预算金额不能为空")
    private Double budgetPrice;
    /**
     * 设置预算时候的备注
     */
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "设置预算的备注只能包含数字、中英文、下换线")
    private String budgetRemark;
}
