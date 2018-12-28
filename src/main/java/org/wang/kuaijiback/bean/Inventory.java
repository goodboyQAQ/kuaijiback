package org.wang.kuaijiback.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wang.kuaijiback.annotation.ExcelTitle;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@ExcelTitle(value={"id","name","type","belong","standard","price","unit","number","amount","remark"},
        title={"编号（不能修改）","名称","类型","所属","规格","单价","单位","数量","总额","备注"})
public class Inventory {
    private Long id;
    private String name;
    private String type;
    private String belong;
    private String standard;
    private BigDecimal price;
    private String unit;
    private Integer number;
    private BigDecimal amount;
    private String remark;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
    private String status;
}
