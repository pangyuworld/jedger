package com.ledger.core.beans.vo.category;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author pang
 * @version V1.0
 * @ClassName: CategoryForm
 * @Package com.ledger.core.beans.vo.category
 * @description: 账目品类表单
 * @date 2020/1/8 19:38
 */
@Data
@ToString
@EqualsAndHashCode
public class CategoryForm {
    public static final String EXPENSES = "支出";
    public static final String INCOME = "收入";

    /**
     * 品类ID
     */
    private Long categoryId;
    /**
     * 品类类型
     */
    @NotNull(message = "账目品类类型不能为空")
    @Length(min = 2, max = 2, message = "账目品类类型必须为“支出”或“收入”")
    private String categoryType;
    /**
     * 品类名
     */
    @NotNull(message = "账目品类名不能为空")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "账目品类名只能包含数字、中英文、下换线")
    @Length(max = 20, message = "账目品类名名长度限制在20个字符以内")
    private String categoryName;
}
