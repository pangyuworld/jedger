package com.ledger.core.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ledger.core.beans.vo.bill.*;
import com.ledger.core.common.rest.ResponseEnum;
import com.ledger.core.common.rest.ResponseJSON;
import com.ledger.core.service.BillService;
import com.ledger.core.util.token.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.List;

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


    @Token
    @RequestMapping(method = RequestMethod.GET)
    public ResponseJSON<List<BillEntireForm>> getAllBill(@RequestAttribute Long userId) {
        log.debug("获取用户全部账单,userId={}", userId);
        return new ResponseJSON<>(billService.getAllBill(userId), ResponseEnum.SUCCESS_OPTION);
    }

    @Token
    @RequestMapping(value = "/time", method = RequestMethod.GET)
    public ResponseJSON<List<BillEntireForm>> getAllBillByTime(@RequestAttribute
                                                                       Long userId,
                                                               @Valid
                                                               @RequestParam
                                                               @NotNull(message = "开始时间不能为空")
                                                               @Past(message = "开始时间必须为以前的一个时间")
                                                               @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd")
                                                                       Date startTime,
                                                               @Valid
                                                               @RequestParam
                                                               @NotNull(message = "结束时间不能为空")
                                                               @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd")
                                                                       Date endTime) {
        log.debug("获取用户一段时间内的账单,userId={},start={},end={}", userId, startTime, endTime);
        return new ResponseJSON<>(billService.getBillByTime(startTime, endTime, userId), ResponseEnum.SUCCESS_OPTION);
    }

    @Token
    @RequestMapping(value = "/day", method = RequestMethod.GET)
    public ResponseJSON<List<BillEntireForm>> getAllBillByDay(@RequestAttribute
                                                                      Long userId,
                                                              @Valid
                                                              @RequestParam
                                                              @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd")
                                                                      Date time) {
        log.debug("获取用户某一天的账单,userId={},time={}", userId, time);
        return new ResponseJSON<>(billService.getBillByDay(time, userId), ResponseEnum.SUCCESS_OPTION);
    }

    @Token
    @RequestMapping(value = "/sum/time/day", method = RequestMethod.GET)
    public ResponseJSON<BillForm> getSumBillByDay(@RequestAttribute
                                                          Long userId,
                                                  @Valid
                                                  @RequestParam
                                                  @NotNull(message = "时间不能为空")
                                                  @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd")
                                                          Date time) {
        log.debug("查询用户某天的总账单,userId={},time={}", userId, time);
        return new ResponseJSON<>(billService.getSumBillByDay(userId, time), ResponseEnum.SUCCESS_OPTION);
    }

    @Token
    @RequestMapping(value = "/sum/time/month", method = RequestMethod.GET)
    public ResponseJSON<BillMonthForm> getSumBillByMonth(@RequestAttribute
                                                                 Long userId,
                                                         @Valid
                                                         @RequestParam
                                                         @NotNull(message = "时间不能为空")
                                                         @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM")
                                                                 Date time) {
        log.debug("查询用户某月的总账单,userId={},time={}", userId, time);
        return new ResponseJSON<>(billService.getSumBillByMonth(userId, time), ResponseEnum.SUCCESS_OPTION);
    }

    @Token
    @RequestMapping(value = "/{billId:[0-9]+$}", method = RequestMethod.DELETE)
    public ResponseJSON<Boolean> deleteBill(@RequestAttribute Long userId, @PathVariable Long billId) {
        log.info("准备删除账单,userId={},billId={}", userId, billId);
        return new ResponseJSON<>(billService.deleteBill(userId, billId), ResponseEnum.SUCCESS_OPTION);
    }

    @Token
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseJSON<BillEntireForm> updateBill(@RequestAttribute Long userId, @RequestBody @Valid BillUpdateForm billUpdateForm) {
        log.info("更新账单信息,userId={},billForm={}", userId, billUpdateForm);
        return new ResponseJSON<>(billService.updateBill(userId, billUpdateForm), ResponseEnum.SUCCESS_OPTION);
    }
}
