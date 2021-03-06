package com.ledger.core.service.impl;

import com.ledger.core.beans.po.Category;
import com.ledger.core.beans.vo.category.CategoryForm;
import com.ledger.core.beans.vo.category.CategoryUpdateForm;
import com.ledger.core.common.exception.UserActionException;
import com.ledger.core.common.rest.ResponseEnum;
import com.ledger.core.mapper.CategoryMapper;
import com.ledger.core.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author pang
 * @version V1.0
 * @ClassName: CategoryServiceImpl
 * @Package com.ledger.core.service.impl
 * @description:
 * @date 2020/1/8 19:42
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加新的账目品类
     *
     * @param categoryForm 账目品类表单
     * @return 新添加的账目品类信息
     */
    @Override
    public CategoryForm addNewCategory(CategoryForm categoryForm) {
        Category category = new Category();
        // 将VO转为PO
        category.setCategoryName(categoryForm.getCategoryName());
        if (categoryForm.getCategoryType().equals(CategoryForm.EXPENSES)) {
            category.setCategoryType(Boolean.TRUE);
        } else if (categoryForm.getCategoryType().equals(CategoryForm.INCOME)) {
            category.setCategoryType(Boolean.FALSE);
        } else {
            log.info("账目品类类型错误,categoryForm={}", categoryForm);
            throw new UserActionException(ResponseEnum.BAD_REQUEST);
        }
        // 转换完成，开始插入
        Boolean result = categoryMapper.addNewCategory(category) > 0;
        log.debug("向数据库插入新的账目品类,category={},result={}", category, result);
        categoryForm.setCategoryId(category.getCategoryId());
        log.debug("添加新的账目品类完成,categoryForm={}", category);
        return categoryForm;
    }

    /**
     * 获取支出账目品类
     *
     * @return 支出账目品类列表
     */
    @Override
    public List<CategoryForm> getExpensesCategory() {
        // 获取原始列表
        List<Category> categoryList = categoryMapper.getExpensesCategory();
        log.debug("获取支出账目品类列表,categoryList={}", categoryList);
        // 将PO转换为VO
        List<CategoryForm> categoryFormList = categoryList2CategoryFormList(categoryList);
        log.debug("获取支出账目品类列表,categoryFormList={}", categoryFormList);
        return categoryFormList;
    }

    /**
     * 获取收入账目品类
     *
     * @return 收入账目品类列表
     */
    @Override
    public List<CategoryForm> getIncomesCategory() {
        // 获取原始列表
        List<Category> categoryList = categoryMapper.getIncomeCategory();
        log.debug("获取收入账目品类列表,categoryList={}", categoryList);
        // 将PO转换为VO
        List<CategoryForm> categoryFormList = categoryList2CategoryFormList(categoryList);
        log.debug("获取收入账目品类列表,categoryFormList={}", categoryFormList);
        return categoryFormList;
    }

    /**
     * 获取全部账目品类
     *
     * @return 全部账目品类
     */
    @Override
    public List<List<CategoryForm>> getAllCategory() {
        List<List<CategoryForm>> categoryListList = new LinkedList<>();
        categoryListList.add(this.getIncomesCategory());
        categoryListList.add(this.getExpensesCategory());
        return categoryListList;
    }

    /**
     * 更新账目品类信息（仅能更新账目品类名，暂不支持修改账目品类类型）
     *
     * @param categoryUpdateForm 要进行更新的内容
     * @return 更新后的账目品类信息
     */
    @Override
    public CategoryForm editCategory(CategoryUpdateForm categoryUpdateForm) {
        log.debug("更新账目品类信息,category={}", categoryUpdateForm);
        // 将VO转为PO
        Category category = new Category();
        category.setCategoryId(categoryUpdateForm.getCategoryId());
        category.setCategoryName(categoryUpdateForm.getCategoryName());
        // 进行数据库更新
        categoryMapper.updateCategory(category);
        log.debug("更新账目品类信息成功,category={}", category);
        // 查询最新的信息
        category = categoryMapper.getCategoryByCategoryId(categoryUpdateForm.getCategoryId());
        CategoryForm categoryForm = new CategoryForm();
        categoryForm.setCategoryId(category.getCategoryId());
        categoryForm.setCategoryName(category.getCategoryName());
        categoryForm.setCategoryType(category.getCategoryType() ? CategoryForm.EXPENSES : CategoryForm.INCOME);
        log.debug("查询最新的账目品类信息,categoryForm={}", categoryForm);
        return categoryForm;
    }

    /**
     * 将PO转换为VO
     *
     * @param categoryList 要转换的PO
     * @return 转换后的VO
     */
    private List<CategoryForm> categoryList2CategoryFormList(List<Category> categoryList) {
        List<CategoryForm> categoryFormList = new LinkedList<>();
        for (Category category : categoryList) {
            CategoryForm categoryForm = new CategoryForm();
            categoryForm.setCategoryId(category.getCategoryId());
            categoryForm.setCategoryName(category.getCategoryName());
            categoryForm.setCategoryType(category.getCategoryType() ? CategoryForm.EXPENSES : CategoryForm.INCOME);
            categoryFormList.add(categoryForm);
        }
        return categoryFormList;
    }
}
