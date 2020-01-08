package com.ledger.core.beans.vo.bill;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ledger.core.beans.vo.category.CategoryForm;
import com.ledger.core.beans.vo.user.UserInfoForm;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * @author pang
 * @version V1.0
 * @ClassName: BillEntireForm
 * @Package com.ledger.core.beans.vo.bill
 * @description: 账单完整信息表单
 * @date 2020/1/8 23:17
 */
@Data
@ToString
@EqualsAndHashCode
public class BillEntireForm {
    /**
     * 记录账单的时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy/MM/dd HH:mm:ss")
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
     * 账目品类
     */
    private CategoryForm category;
    /**
     * 用户信息
     */
    private UserInfoForm userInfo;

}
