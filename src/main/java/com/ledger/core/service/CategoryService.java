package com.ledger.core.service;

import com.ledger.core.beans.vo.category.CategoryForm;
import com.ledger.core.beans.vo.category.CategoryUpdateForm;

import java.util.List;

/**
 * @author pang
 * @version V1.0
 * @ClassName: CategoryService
 * @Package com.ledger.core.service
 * @description: 账目品类服务接口
 * @date 2020/1/8 19:38
 */
public interface CategoryService {
    /**
     * 添加新的账目品类
     *
     * @param categoryForm 账目品类表单
     * @return 新添加的账目品类信息
     */
    CategoryForm addNewCategory(CategoryForm categoryForm);

    /**
     * 获取支出账目品类
     *
     * @return 支出账目品类列表
     */
    List<CategoryForm> getExpensesCategory();

    /**
     * 获取收入账目品类
     *
     * @return 收入账目品类列表
     */
    List<CategoryForm> getIncomesCategory();

    /**
     * 更新账目品类信息（仅能更新账目品类名，暂不支持修改账目品类类型）
     *
     * @param categoryUpdateForm 要进行更新的内容
     * @return 更新后的账目品类信息
     */
    CategoryForm editCategory(CategoryUpdateForm categoryUpdateForm);

}
