package com.ledger.core.controller;

import com.ledger.core.beans.vo.category.CategoryForm;
import com.ledger.core.beans.vo.category.CategoryUpdateForm;
import com.ledger.core.common.rest.ResponseEnum;
import com.ledger.core.common.rest.ResponseJSON;
import com.ledger.core.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author pang
 * @version V1.0
 * @ClassName: CategoryController
 * @Package com.ledger.core.controller
 * @description: 账目品类控制器
 * @date 2020/1/8 19:53
 */
@RestController
@RequestMapping("/api/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseJSON<CategoryForm> addNewCategory(@RequestBody @Valid CategoryForm categoryForm) {
        categoryForm = categoryService.addNewCategory(categoryForm);
        log.debug("添加新的账目品类,category={}", categoryForm);
        return new ResponseJSON<>(categoryForm, ResponseEnum.SUCCESS_OPTION);
    }


    @RequestMapping(value = "/expenses", method = RequestMethod.GET)
    public ResponseJSON<List<CategoryForm>> getExpensesCategory() {
        List<CategoryForm> categoryFormList = categoryService.getExpensesCategory();
        log.debug("获取支出账目品类列表,categoryFormList={}", categoryFormList);
        return new ResponseJSON<>(categoryFormList, ResponseEnum.SUCCESS_OPTION);
    }


    @RequestMapping(value = "/incomes", method = RequestMethod.GET)
    public ResponseJSON<List<CategoryForm>> getIncomesCategory() {
        List<CategoryForm> categoryFormList = categoryService.getIncomesCategory();
        log.debug("获取收入账目品类列表,categoryFormList={}", categoryFormList);
        return new ResponseJSON<>(categoryFormList, ResponseEnum.SUCCESS_OPTION);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseJSON<CategoryForm> updateCategory(@RequestBody @Valid CategoryUpdateForm categoryUpdateForm) {
        log.debug("更新账目品类信息,categoryUpdateForm={}", categoryUpdateForm);
        return new ResponseJSON<>(categoryService.editCategory(categoryUpdateForm), ResponseEnum.SUCCESS_OPTION);
    }
}
