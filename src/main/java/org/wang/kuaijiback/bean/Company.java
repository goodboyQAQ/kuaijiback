package org.wang.kuaijiback.bean;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.wang.kuaijiback.annotation.ExcelTitle;
import org.wang.kuaijiback.annotation.Fields;


import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"status","createTime","updateTime"})
@ExcelTitle(value={"id","companyName","taxpayer","address","phone","bank","accoutId","corporation","tel","remark"},
        title={"编号（不能修改）","公司名称","纳税人识别号","公司地址", "公司电话","开户行","银行卡号","法人","法人电话","备注"})
public class Company implements Serializable {

    private String id;
    private String companyName;
    private String taxpayer;
    private String address;
    private String phone;
    private String bank;
    private String accoutId;
    private String corporation;
    private String tel;
    private String remark;
    private String status;
    private Date createTime;
    private Date updateTime;

}
