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
 * @ClassName: BillAddForm
 * @Package com.ledger.core.beans.vo.bill
 * @description: 添加账单表单
 * @date 2020/1/8 21:51
 */
@Data
@ToString
@EqualsAndHashCode
public class BillAddForm {
    /**
     * 记录账单的时间
     */
    @NotNull(message = "记录时间不能为空")
    @Past(message = "不能记录未来的账")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy/MM/dd HH:mm:ss")
    private Date billTime;
    /**
     * 记录账单的金额
     */
    @NotNull(message = "记录金额不能为空")
    @Min(value = 0, message = "记录金额不能小于0")
    private Double billPrice;
    /**
     * 记录账单时候的备注
     */
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "账单日志只能包含数字、中英文、下换线")
    private String billRemark;
    /**
     * 账单品类
     */
    @Min(value = 0, message = "记录类型不存在")
    private Long category;
}
