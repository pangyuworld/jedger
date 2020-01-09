package com.ledger.core.mapper;

import com.ledger.core.beans.po.Budget;

import java.util.Date;
import java.util.List;

/**
 * @author pang
 * @version V1.0
 * @ClassName: BudgetMapper
 * @Package com.ledger.core.mapper
 * @description: 预算表数据库操作
 * @date 2020/1/9 13:34
 */
public interface BudgetMapper {

    /**
     * 添加新的预算
     *
     * @param budget 预算信息
     * @return 添加成功返回1
     */
    Integer addNewBudget(Budget budget);


    /**
     * 获取用户某月的预算记录
     *
     * @param userId 用户ID
     * @param time   要查询的时间（精确到月）
     * @return 预算记录信息
     */
    Budget getBudgetByTime(Long userId, Date time);

    /**
     * 获取用户全部的预算信息
     *
     * @return 预算记录列表
     */
    List<Budget> getAllBudget(Long userId);

    /**
     * 更新预算信息（仅支持更新金额和备注）
     *
     * @param budget 预算信息
     * @return 执行成功返回1
     */
    Integer updateBudget(Budget budget);

    /**
     * 根据ID获取预算信息（仅支持在系统内部调用，不公开）
     *
     * @param budgetId 预算ID
     * @return 预算信息
     */
    Budget getBudgetByBudgetId(Long budgetId);

    /**
     * 删除预算信息，需要用户ID和预算ID共同匹配
     *
     * @param userId   用户ID
     * @param budgetId 预算ID
     * @return 删除成功返回1
     */
    Integer deleteBudget(Long userId, Long budgetId);
}
