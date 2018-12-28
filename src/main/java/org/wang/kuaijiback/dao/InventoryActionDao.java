package org.wang.kuaijiback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.wang.kuaijiback.bean.InventoryAction;

import java.util.List;

@Mapper
public interface InventoryActionDao extends BaseMapper<InventoryAction> {
    void insertOne(InventoryAction inventoryAction);
    void insertList(List<InventoryAction> list);
}
