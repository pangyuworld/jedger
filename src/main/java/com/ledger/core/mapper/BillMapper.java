package com.ledger.core.mapper;

import com.ledger.core.beans.po.Bill;
import com.ledger.core.beans.po.BillEntire;

import java.util.Date;
import java.util.List;

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
     *
     * @param bill 账单的详细信息
     * @return 添加成功返回1
     */
    Integer addNewBill(Bill bill);

    /**
     * 获取用户所有的账单
     *
     * @param userId 用户Id
     * @return 账单列表
     */
    List<BillEntire> getAllBill(Long userId);

    /**
     * 获取用户规定时间内的账单
     */
    List<BillEntire> getBillByTime(Date startTime, Date endTime, Long userId);
}
