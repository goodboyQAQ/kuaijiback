package org.wang.kuaijiback.bean;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class Result<T> {
    private String msg;
    private boolean success;
    private Object data;

    public Result(){
        this.success=false;
        this.msg="系统错误";
    }

    public Result(boolean b,String msg){
        this.success=b;
        this.msg=msg;
    }

    public Result ok(String msg){
        this.msg=msg;
        this.success=true;
        return this;
    }
    public Result ok(String msg,Object data){
        this.msg=msg;
        this.success=true;
        this.data=data;
        return this;
    }

}
