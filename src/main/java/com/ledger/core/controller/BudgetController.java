package com.ledger.core.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ledger.core.beans.vo.budget.BudgetAddFrom;
import com.ledger.core.beans.vo.budget.BudgetForm;
import com.ledger.core.beans.vo.budget.BudgetUpdateFrom;
import com.ledger.core.common.rest.ResponseEnum;
import com.ledger.core.common.rest.ResponseJSON;
import com.ledger.core.service.BudgetService;
import com.ledger.core.util.token.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author pang
 * @version V1.0
 * @ClassName: BudgetController
 * @Package com.ledger.core.controller
 * @description: 预算控制器
 * @date 2020/1/9 14:07
 */
@RestController
@Slf4j
@RequestMapping("/api/budget")
public class BudgetController {
    @Autowired
    private BudgetService budgetService;


    @Token
    @RequestMapping(method = RequestMethod.POST)
    public ResponseJSON<Boolean> addNewBudget(@RequestAttribute Long userId, @RequestBody @Valid BudgetAddFrom budgetAddFrom) {
        log.debug("添加新的预算记录,userId={},budget={}", userId, budgetAddFrom);
        return new ResponseJSON<>(budgetService.addNewBudget(userId, budgetAddFrom), ResponseEnum.SUCCESS_OPTION);
    }

    @Token
    @RequestMapping(value = "/time", method = RequestMethod.GET)
    public ResponseJSON<BudgetForm> getBudgetByTime(@RequestAttribute
                                                            Long userId,
                                                    @Valid
                                                    @RequestParam(required = false)
                                                    @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd")
                                                            Date time) {
        log.debug("获取用户某月的预算信息,userId={},time={}", userId, time);
        return new ResponseJSON<>(budgetService.getBudgetByMonth(userId, time), ResponseEnum.SUCCESS_OPTION);
    }

    @Token
    @RequestMapping(method = RequestMethod.GET)
    public ResponseJSON<List<BudgetForm>> getAllBudget(@RequestAttribute Long userId) {
        log.debug("获取用户所有的预算信息,userId={}", userId);
        return new ResponseJSON<>(budgetService.getAllBudget(userId), ResponseEnum.SUCCESS_OPTION);
    }


    @Token
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseJSON<BudgetForm> editBudget(@RequestAttribute Long userId,
                                               @RequestBody @Valid BudgetUpdateFrom budgetUpdateFrom) {
        log.debug("更新预算信息,userId={},budget={}", userId, budgetUpdateFrom);
        return new ResponseJSON<>(budgetService.editBudget(userId, budgetUpdateFrom), ResponseEnum.SUCCESS_OPTION);
    }

    @Token
    @RequestMapping(value = "/{budgetId:[0-9]+$}", method = RequestMethod.DELETE)
    public ResponseJSON<Boolean> deleteBudget(@RequestAttribute Long userId,
                                              @PathVariable Long budgetId) {
        log.info("删除预算信息,userId={},budgetId={}", userId, budgetId);
        return new ResponseJSON<>(budgetService.deleteBudget(userId, budgetId), ResponseEnum.SUCCESS_OPTION);
    }
}
