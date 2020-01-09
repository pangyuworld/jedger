package com.ledger.core.service;

import com.ledger.core.beans.vo.bill.*;

import java.util.Date;
import java.util.List;

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

    /**
     * 获取用户的全部账单
     *
     * @param userId 用户ID
     * @return 用户的全部账单
     */
    List<BillEntireForm> getAllBill(Long userId);

    /**
     * 获取用户某一时间段内的账单
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param userId    用户ID
     * @return 用户在该段时间的账单
     */
    List<BillEntireForm> getBillByTime(Date startTime, Date endTime, Long userId);

    /**
     * 获取用户某一天的账单
     *
     * @param time   时间（精确到天（默认为今天））
     * @param userId 用户Id
     * @return 用户在该天的账单
     */
    List<BillEntireForm> getBillByDay(Date time, Long userId);

    /**
     * 获取用户某天的总账单（金额计算为和）
     *
     * @param userId 用户ID
     * @param time   时间，精确到天
     * @return 用户该日的总账单
     */
    BillForm getSumBillByDay(Long userId, Date time);

    /**
     * 获取用户某月的总账单(按天明细)
     *
     * @param userId 用户ID
     * @param time   时间，精确到月
     * @return 用户该月的账单
     */
    BillMonthForm getSumBillByMonth(Long userId, Date time);

    /**
     * 删除账单，必须要用户ID和账单ID共同匹配
     *
     * @param userId 用户ID
     * @param billId 账单ID
     * @return 删除是否成功
     */
    Boolean deleteBill(Long userId, Long billId);

    /**
     * 更新账单信息
     *
     * @param userId         用户ID
     * @param billUpdateForm 账单更新的表单
     * @return 更新后的账单信息
     */
    BillEntireForm updateBill(Long userId, BillUpdateForm billUpdateForm);

}
