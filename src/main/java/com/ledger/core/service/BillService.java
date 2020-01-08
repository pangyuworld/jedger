package com.ledger.core.service;

import com.ledger.core.beans.vo.bill.BillAddForm;

/**
 * @author pang
 * @version V1.0
 * @ClassName: BillService
 * @Package com.ledger.core.service
 * @description: 账单服务接口
 * @date 2020/1/8 21:51
 */
public interface BillService {
    /**
     * 添加新的账单
     *
     * @param billAddForm 添加账单表单
     * @param userId      添加账单的用户
     * @return 是否添加成功
     */
    Boolean addNewBill(BillAddForm billAddForm, Long userId);
}
