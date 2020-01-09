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
     *
     * @param userId    用户ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    List<BillEntire> getBillByTime(Date startTime, Date endTime, Long userId);

    /**
     * 获取用户某一天的账单总和
     *
     * @param userId   用户ID
     * @param time     时间
     * @param nextTime 下一天的时间
     * @return 用户账单（对金额进行求和）
     */
    Bill getSumBillByDay(Long userId, Date time, Date nextTime);

    /**
     * 获取用户某一月的账单总和
     *
     * @param userId   用户ID
     * @param time     开始时间（精确每月）
     * @param nextTime 结束时间（精确到每月）
     * @return 用户一整月的账单总和
     */
    List<Bill> getSumBillByMonth(Long userId, Date time, Date nextTime);

    /**
     * 删除账单，必须要用户ID和账单ID共同匹配
     *
     * @param userId 用户ID
     * @param billId 账单ID
     * @return 删除成功返回1, 不成功返回0
     */
    Integer deleteBill(Long userId, Long billId);

    /**
     * 更新账单信息，必须要用户ID和账单ID共同匹配
     *
     * @param bill 账单信息
     * @return 只要数据库操作成功就返回1
     */
    Integer editBill(Bill bill);

    /**
     * 根据ID获取账单信息，必须要用户ID和账单ID共同匹配
     *
     * @param userId 用户ID
     * @param billId 账单ID
     * @return 账单信息
     */
    BillEntire getBillById(Long userId, Long billId);
}
