package com.ledger.core.service.impl;

import com.ledger.core.beans.po.Budget;
import com.ledger.core.beans.vo.budget.BudgetAddFrom;
import com.ledger.core.beans.vo.budget.BudgetForm;
import com.ledger.core.beans.vo.budget.BudgetUpdateFrom;
import com.ledger.core.common.exception.UserActionException;
import com.ledger.core.common.rest.ResponseEnum;
import com.ledger.core.mapper.BudgetMapper;
import com.ledger.core.service.BudgetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author pang
 * @version V1.0
 * @ClassName: BudgetServiceImpl
 * @Package com.ledger.core.service.impl
 * @description:
 * @date 2020/1/9 13:48
 */
@Slf4j
@Service
public class BudgetServiceImpl implements BudgetService {
    @Autowired
    private BudgetMapper budgetMapper;

    /**
     * 添加新的预算记录
     *
     * @param userId        添加预算的用户ID
     * @param budgetAddFrom 预算记录信息
     * @return 是否添加成功
     */
    @Override
    public Boolean addNewBudget(Long userId, BudgetAddFrom budgetAddFrom) {
        // 将时间设置为本月第一天
        budgetAddFrom.setBudgetTime(getFirstDayOfMonth(budgetAddFrom.getBudgetTime()));
        log.debug("添加新的预算记录,userId={},budget={}", userId, budgetAddFrom);
        // VO转为PO
        Budget budget = new Budget();
        budget.setBudgetPrice(budgetAddFrom.getBudgetPrice());
        budget.setBudgetRemark(budgetAddFrom.getBudgetRemark());
        budget.setBudgetTime(budgetAddFrom.getBudgetTime());
        budget.setUserId(userId);
        // 执行真正的数据库语句
        try {
            budgetMapper.addNewBudget(budget);
        } catch (DuplicateKeyException e) {
            log.info("每个用户每个月只能设置一次预算,budget={}", budget, e);
            throw new UserActionException(ResponseEnum.BUDGET_EXIST);
        }
        log.debug("添加新的预算记录,budget={}", budget);
        return true;
    }

    /**
     * 获取用户某月的预算信息
     *
     * @param userId 用户ID
     * @param time   时间，精确到月
     * @return 预算信息
     */
    @Override
    public BudgetForm getBudgetByMonth(Long userId, Date time) {
        // 判断时间信息，赋予默认值
        if (time == null) {
            time = new Date();
        }
        // 将时间设置为本月的第一天
        time = getFirstDayOfMonth(time);
        log.debug("获取用户某月的预算信息,userId={},time={}", userId, time);
        // 进行数据库查询
        Budget budget = budgetMapper.getBudgetByTime(userId, time);
        log.debug("获取用户某月的预算信息,budget={}", budget);
        // 将PO组装成VO
        BudgetForm budgetForm = new BudgetForm();
        budgetForm.setBudgetId(budget.getBudgetId());
        budgetForm.setBudgetPrice(budget.getBudgetPrice());
        budgetForm.setBudgetRemark(budget.getBudgetRemark());
        budgetForm.setBudgetTime(budget.getBudgetTime());
        return budgetForm;
    }

    /**
     * 查询用户所有的预算
     *
     * @param userId 用户ID
     * @return 预算信息列表
     */
    @Override
    public List<BudgetForm> getAllBudget(Long userId) {
        log.debug("查询用户所有的预算信息,userId={}", userId);
        // 从数据库查询数据
        List<Budget> budgetList = budgetMapper.getAllBudget(userId);
        List<BudgetForm> budgetFormList = new LinkedList<>();
        // 将PO转换为VO
        for (Budget budget : budgetList) {
            BudgetForm budgetForm = new BudgetForm();
            budgetForm.setBudgetId(budget.getBudgetId());
            budgetForm.setBudgetPrice(budget.getBudgetPrice());
            budgetForm.setBudgetRemark(budget.getBudgetRemark());
            budgetForm.setBudgetTime(budget.getBudgetTime());
            budgetFormList.add(budgetForm);
        }
        log.debug("查询到用户所有的预算信息,userId={},budgetFormList={}", userId, budgetFormList);
        return budgetFormList;
    }

    /**
     * 更新预算，需要用户ID和预算ID共同匹配才能修改
     *
     * @param userId           用户ID
     * @param budgetUpdateFrom 更改的预算信息
     * @return 更新后的预算信息
     */
    @Override
    public BudgetForm editBudget(Long userId, BudgetUpdateFrom budgetUpdateFrom) {
        log.debug("更新预算信息,userId={},budget={}", userId, budgetUpdateFrom);
        // 将VO转换为PO
        Budget budget = new Budget();
        budget.setUserId(userId);
        budget.setBudgetPrice(budgetUpdateFrom.getBudgetPrice());
        budget.setBudgetRemark(budgetUpdateFrom.getBudgetRemark());
        budget.setBudgetId(budgetUpdateFrom.getBudgetId());
        // 执行数据库语句
        budgetMapper.updateBudget(budget);
        // 将最新的数据查询出来
        budget = budgetMapper.getBudgetByBudgetId(budget.getBudgetId());
        // 将PO转换为VO
        BudgetForm budgetForm = new BudgetForm();
        budgetForm.setBudgetId(budget.getBudgetId());
        budgetForm.setBudgetPrice(budget.getBudgetPrice());
        budgetForm.setBudgetRemark(budget.getBudgetRemark());
        budgetForm.setBudgetTime(budget.getBudgetTime());
        log.debug("更新预算信息成功,budget={}", budgetForm);
        return budgetForm;
    }

    /**
     * 删除预算
     *
     * @param userId   用户ID
     * @param budgetId 预算ID
     * @return 删除是否成功
     */
    @Override
    public Boolean deleteBudget(Long userId, Long budgetId) {
        boolean result = budgetMapper.deleteBudget(userId, budgetId) > 0;
        log.info("删除预算信息,userId={},budgetId={},result={}", userId, budgetId, result);
        if (!result){
            throw new UserActionException(ResponseEnum.NOT_FOUND);
        }
        return true;
    }

    /**
     * 获取某月的第一天
     *
     * @param date 时间信息，精确到月
     * @return 该月的第一天的时间
     */
    private Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
}
