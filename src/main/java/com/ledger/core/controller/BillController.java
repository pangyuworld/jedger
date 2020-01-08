package com.ledger.core.controller;

import com.ledger.core.beans.vo.bill.BillAddForm;
import com.ledger.core.common.rest.ResponseEnum;
import com.ledger.core.common.rest.ResponseJSON;
import com.ledger.core.service.BillService;
import com.ledger.core.util.token.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author pang
 * @version V1.0
 * @ClassName: BillController
 * @Package com.ledger.core.controller
 * @description: 账单控制器
 * @date 2020/1/8 22:11
 */
@RestController
@Slf4j
@RequestMapping("/api/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @Token
    @RequestMapping(method = RequestMethod.POST)
    public ResponseJSON<Boolean> addNewBill(@RequestBody @Valid BillAddForm billAddForm, @RequestAttribute Long userId) {
        Boolean result = billService.addNewBill(billAddForm, userId);
        log.debug("添加账单,billAddForm={},userId={}", billAddForm, userId);
        return new ResponseJSON<>(result, ResponseEnum.SUCCESS_OPTION);
    }
}
