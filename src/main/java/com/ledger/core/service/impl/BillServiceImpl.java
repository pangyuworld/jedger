package com.ledger.core.service.impl;

import com.ledger.core.beans.po.Bill;
import com.ledger.core.beans.vo.bill.BillAddForm;
import com.ledger.core.common.exception.UserActionException;
import com.ledger.core.common.rest.ResponseEnum;
import com.ledger.core.mapper.BillMapper;
import com.ledger.core.mapper.CategoryMapper;
import com.ledger.core.service.BillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pang
 * @version V1.0
 * @ClassName: BillServiceImpl
 * @Package com.ledger.core.service.impl
 * @description:
 * @date 2020/1/8 21:56
 */
@Service
@Slf4j
public class BillServiceImpl implements BillService {
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加新的账单
     *
     * @param billAddForm 添加账单表单
     * @param userId      添加账单的用户
     * @return 是否添加成功
     */
    @Override
    public Boolean addNewBill(BillAddForm billAddForm, Long userId) {
        // 检查账单品类是否存在
        Boolean exists = categoryMapper.existsByCategoryId(billAddForm.getCategory());
        log.debug("判断账单品类是否存在,categoryId={},exists={}", billAddForm.getCategory(), exists != null);
        if (exists == null) {
            log.debug("账单品类不存在,billAddForm={}", billAddForm);
            throw new UserActionException(ResponseEnum.BAD_REQUEST);
        }
        // VO转为PO
        Bill bill = new Bill();
        bill.setBillPrice(exists ? -1 * billAddForm.getBillPrice() : billAddForm.getBillPrice());
        bill.setBillRemark(billAddForm.getBillRemark());
        bill.setBillTime(billAddForm.getBillTime());
        bill.setCategoryId(billAddForm.getCategory());
        bill.setUserId(userId);
        // 向数据库插入账单
        boolean result = billMapper.addNewBill(bill) > 0;
        log.debug("向数据库添加账单,bill={},result={}", bill, result);
        return result;
    }
}
