package com.ledger.core.mapper;

import com.ledger.core.beans.po.Category;

import java.util.List;

/**
 * @author pang
 * @version V1.0
 * @ClassName: CategoryMapper
 * @Package com.ledger.core.mapper
 * @description: 账目品类数据库操作
 * @date 2020/1/8 19:34
 */
public interface CategoryMapper {
    /**
     * 添加账目品类
     *
     * @param category 账目品类信息
     * @return 添加成功返回1
     */
    Integer addNewCategory(Category category);

    /**
     * 获取支出账目品类
     *
     * @return 支出账目品类的列表
     */
    List<Category> getExpensesCategory();

    /**
     * 获取收入账目品类
     *
     * @return 收入账目品类列表
     */
    List<Category> getIncomeCategory();

    /**
     * 根据ID判断账目品类是否存在
     *
     * @param categoryId 账目品列ID
     * @return 存在返回1，不存在返回0
     */
    Boolean existsByCategoryId(Long categoryId);
}
