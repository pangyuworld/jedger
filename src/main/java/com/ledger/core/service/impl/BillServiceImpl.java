package com.ledger.core.service.impl;

import com.ledger.core.beans.po.Bill;
import com.ledger.core.beans.po.BillEntire;
import com.ledger.core.beans.vo.bill.BillAddForm;
import com.ledger.core.beans.vo.bill.BillEntireForm;
import com.ledger.core.beans.vo.category.CategoryForm;
import com.ledger.core.beans.vo.user.UserInfoForm;
import com.ledger.core.common.exception.UserActionException;
import com.ledger.core.common.rest.ResponseEnum;
import com.ledger.core.mapper.BillMapper;
import com.ledger.core.mapper.CategoryMapper;
import com.ledger.core.service.BillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

    /**
     * 获取用户的全部账单
     *
     * @param userId 用户ID
     * @return 用户的全部账单
     */
    @Override
    public List<BillEntireForm> getAllBill(Long userId) {
        // 获取全部订单
        List<BillEntire> billEntireList = billMapper.getAllBill(userId);

        List<BillEntireForm> billEntireFormList = billEntireList2BillEntireFormList(billEntireList);
        log.debug("获取用户全部订单,userId={},billEntireFormList={}", userId, billEntireFormList);
        // 将订单PO转换为VO
        return billEntireFormList;
    }

    /**
     * 获取用户某一时间段内的账单
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param userId    用户ID
     * @return 用户在该段时间的账单
     */
    @Override
    public List<BillEntireForm> getBillByTime(Date startTime, Date endTime, Long userId) {
        // 获取全部订单
        List<BillEntire> billEntireList = billMapper.getBillByTime(startTime, endTime, userId);
        // 将订单PO转换为VO
        List<BillEntireForm> billEntireFormList = billEntireList2BillEntireFormList(billEntireList);
        log.debug("获取一段时间内的订单全部订单,userId={},startTime={},endTime={}", userId, startTime, endTime);
        return billEntireFormList;
    }

    /**
     * 将PO转换为VO
     */
    private List<BillEntireForm> billEntireList2BillEntireFormList(List<BillEntire> billEntireList) {
        List<BillEntireForm> billEntireFormList = new LinkedList<>();
        for (BillEntire billEntire : billEntireList) {
            BillEntireForm billEntireForm = new BillEntireForm();
            UserInfoForm userInfoForm = new UserInfoForm();
            CategoryForm categoryForm = new CategoryForm();
            billEntireForm.setBillPrice(billEntire.getBillPrice());
            billEntireForm.setBillRemark(billEntire.getBillRemark());
            billEntireForm.setBillTime(billEntire.getBillTime());
            userInfoForm.setUserGender(billEntire.getUserInfo()
                    .getUserGender() ? UserInfoForm.MAN : UserInfoForm.WOMAN);
            userInfoForm.setUserRealName(billEntire.getUserInfo().getUserRealName());
            billEntireForm.setUserInfo(userInfoForm);
            categoryForm.setCategoryName(billEntire.getCategory().getCategoryName());
            categoryForm.setCategoryType(billEntire.getCategory()
                    .getCategoryType() ? CategoryForm.INCOME : CategoryForm.EXPENSES);
            billEntireForm.setCategory(categoryForm);
            billEntireFormList.add(billEntireForm);
        }
        return billEntireFormList;
    }
}
