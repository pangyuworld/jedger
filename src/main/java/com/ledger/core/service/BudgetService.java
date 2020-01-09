package com.ledger.core.service;

import com.ledger.core.beans.vo.budget.BudgetAddFrom;
import com.ledger.core.beans.vo.budget.BudgetForm;
import com.ledger.core.beans.vo.budget.BudgetUpdateFrom;

import java.util.Date;
import java.util.List;

/**
 * @author pang
 * @version V1.0
 * @ClassName: BudgetService
 * @Package com.ledger.core.service
 * @description: 预算服务接口
 * @date 2020/1/9 13:46
 */
public interface BudgetService {

    /**
     * 添加新的预算记录
     *
     * @param userId        添加预算的用户ID
     * @param budgetAddFrom 预算记录信息
     * @return 是否添加成功
     */
    Boolean addNewBudget(Long userId, BudgetAddFrom budgetAddFrom);

    /**
     * 获取用户某月的预算信息
     *
     * @param userId 用户ID
     * @param time   时间，精确到月
     * @return 预算信息
     */
    BudgetForm getBudgetByMonth(Long userId, Date time);

    /**
     * 查询用户所有的预算
     *
     * @param userId 用户ID
     * @return 预算信息列表
     */
    List<BudgetForm> getAllBudget(Long userId);

    /**
     * 更新预算，需要用户ID和预算ID共同匹配才能修改
     *
     * @param userId           用户ID
     * @param budgetUpdateFrom 更改的预算信息
     * @return 更新后的预算信息
     */
    BudgetForm editBudget(Long userId, BudgetUpdateFrom budgetUpdateFrom);

    /**
     * 删除预算
     *
     * @param userId   用户ID
     * @param budgetId 预算ID
     * @return 删除是否成功
     */
    Boolean deleteBudget(Long userId, Long budgetId);
}
