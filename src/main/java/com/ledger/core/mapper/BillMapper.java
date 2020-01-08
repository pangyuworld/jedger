package com.ledger.core.mapper;

import com.ledger.core.beans.po.Bill;

/**
 * @author pang
 * @version V1.0
 * @ClassName: BillMapper
 * @Package com.ledger.core.mapper
 * @description: 账单数据库操作
 * @date 2020/1/8 21:48
 */
public interface BillMapper {
    /**
     * 添加新的账单
     * @param bill 账单的详细信息
     * @return 添加成功返回1
     */
    Integer addNewBill(Bill bill);
}
