package com.ledger.core.beans.vo.bill;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author pang
 * @version V1.0
 * @ClassName: BillUpdateForm
 * @Package com.ledger.core.beans.vo.bill
 * @description: 账单更新表单
 * @date 2020/1/9 10:59
 */
@Data
@ToString
@EqualsAndHashCode
public class BillUpdateForm {
    /**
     * 账单ID
     */
    @NotNull(message = "账单ID不能为空")
    @Min(value = 0, message = "账单ID不能小于0")
    private Long billId;
    /**
     * 记录账单的时间
     */
    @Past(message = "记账时间不能是未来的时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd HH:mm:ss")
    private Date billTime;
    /**
     * 记录账单的金额
     */
    @Min(value = 0, message = "记账金额不能小于0")
    private Double billPrice;
    /**
     * 记录账单时候的备注
     */
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "账单日志只能包含数字、中英文、下换线")
    private String billRemark;
    /**
     * 账单品类
     */
    @Min(value = 0, message = "账单品类ID不能小于0")
    private Long categoryId;
}
