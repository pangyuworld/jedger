package com.ledger.core.beans.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author pang
 * @version V1.0
 * @ClassName: Category
 * @Package com.ledger.core.beans.po
 * @description: 记账品类表
 * @date 2020/1/8 19:32
 */
@Data
@ToString
@EqualsAndHashCode
public class Category {
    /**
     * 品类ID
     */
    private Long categoryId;
    /**
     * 品类类型（true为支出，false为收入）
     */
    private Boolean categoryType;
    /**
     * 品类名
     */
    private String categoryName;
}
