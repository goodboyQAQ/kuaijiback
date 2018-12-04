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



    @Fields("编号（不能修改）")
    private String id;
    @Fields("公司名称")
    private String companyName;
    @Fields("纳税人识别号")
    private String taxpayer;
    @Fields("公司地址")
    private String address;
    @Fields("公司电话")
    private String phone;
    @Fields("开户行")
    private String bank;
    @Fields("银行卡号")
    private String accoutId;
    @Fields("法人")
    private String corporation;
    @Fields("法人电话")
    private String tel;
    @Fields("备注")
    private String remark;
    private String status;
    private Date createTime;
    private Date updateTime;

}
