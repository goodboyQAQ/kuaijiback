package org.wang.kuaijiback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.wang.kuaijiback.bean.Inventory;
import org.wang.kuaijiback.bean.PageForm;
import org.wang.kuaijiback.bean.Param;

import java.util.List;

public interface InventoryService{
    //分页
    Page<Inventory> getPage(PageForm pageForm);

    List<Inventory> getList(Param param);
    //增
    void insertInventory(Inventory inventory);
    //改
    void updateInventory(Inventory inventory);
    //删
    void deleteInventory(Long id);
    //批量导入  同时更新与插入
    void importList(List<Inventory> list);

    Inventory getById(Long id);

    int getNumById(Long id);
}
