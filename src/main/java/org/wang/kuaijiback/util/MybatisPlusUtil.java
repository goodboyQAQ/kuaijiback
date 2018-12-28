package org.wang.kuaijiback.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;

@Slf4j
public class MybatisPlusUtil<T> {

    //全like
    public static<T> QueryWrapper<T> allLike(Map<String,String> map,boolean statusExist){
        QueryWrapper queryWrapper=new QueryWrapper<T>();
        if(map!=null) {
            for (String key : map.keySet()) {
                //前端可能传入空或null值，不加入sql
                if(StringUtil.isNotEmpty(map.get(key))) {
                    String _key=StringUtil.camelToUnderline(key);
                    queryWrapper.like(_key, map.get(key));
                }
            }
        }
        if(statusExist){  //如果表有status字段，查询未删除的数据
            queryWrapper.eq("status",0);
        }
        queryWrapper.orderByDesc("update_time");
        return queryWrapper;
    }

    //逻辑删除
    public static<T> UpdateWrapper<T> delete_status(int id){
        UpdateWrapper updateWrapper=new UpdateWrapper<T>();
        updateWrapper.eq("id",id);
        updateWrapper.set("status",1);
        return updateWrapper;
    }
}
