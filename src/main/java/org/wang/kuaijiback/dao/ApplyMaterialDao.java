package org.wang.kuaijiback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.wang.kuaijiback.bean.ApplyMaterial;
import org.wang.kuaijiback.bean.Company;
import org.wang.kuaijiback.bean.Inventory;

import java.util.List;


@Mapper
public interface ApplyMaterialDao extends BaseMapper<ApplyMaterial> {
    void insertOne(ApplyMaterial applyMaterial);
    void insertList(List<ApplyMaterial> list);
}
