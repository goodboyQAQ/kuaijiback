package org.wang.kuaijiback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.wang.kuaijiback.bean.InventoryAction;
import org.wang.kuaijiback.bean.PageForm;
import org.wang.kuaijiback.bean.Param;
import org.wang.kuaijiback.exception.NumException;

import java.util.List;

public interface InventoryActionService {
    //分页
    Page<InventoryAction> getPage(PageForm pageForm);

    List<InventoryAction> getList(Param param);
    //增
    void insertInventoryAction(InventoryAction inventoryAction);
    //改
    void updateInventoryAction(InventoryAction inventoryAction);
    //删
    void deleteInventoryAction(Long id);
    //批量导入  同时更新与插入
    void importList(List<InventoryAction> list);
    //根据id获取
    InventoryAction getById(Long id);


    //确认出库入库
    void confirm(InventoryAction inventoryAction) throws NumException;
}
