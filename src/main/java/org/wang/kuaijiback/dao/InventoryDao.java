package org.wang.kuaijiback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.wang.kuaijiback.bean.Inventory;

import java.util.List;

@Mapper
public interface InventoryDao extends BaseMapper<Inventory> {
    void insertOne(Inventory inventory);
    void insertList(List<Inventory> list);

    /**
     * 根据库存id获取数量
     * 提供给库存操作
     * @param id
     */
    int getNumById(Long id);
}
