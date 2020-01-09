package com.ledger.core.beans.vo.category;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author pang
 * @version V1.0
 * @ClassName: CategoryUpdateForm
 * @Package com.ledger.core.beans.vo.category
 * @description: 账目品类更新表单
 * @date 2020/1/9 13:09
 */
@Data
@ToString
@EqualsAndHashCode
public class CategoryUpdateForm {

    /**
     * 品类ID
     */
    @NotNull(message = "账目品类ID不能为空")
    @Min(value = 0, message = "账目品类ID不能小于0")
    private Long categoryId;


    /**
     * 品类名
     */
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "账目品类名只能包含数字、中英文、下换线")
    @Length(max = 20, message = "账目品类名名长度限制在20个字符以内")
    private String categoryName;
}
