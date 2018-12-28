package org.wang.kuaijiback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wang.kuaijiback.bean.Inventory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.wang.kuaijiback.bean.PageForm;
import org.wang.kuaijiback.bean.Param;
import org.wang.kuaijiback.dao.InventoryDao;
import org.wang.kuaijiback.service.InventoryService;
import org.wang.kuaijiback.util.MybatisPlusUtil;

import java.math.BigDecimal;
import java.util.*;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    InventoryDao inventoryDao;

    //分页查询
    @Override
    public Page<Inventory> getPage(PageForm pageForm) {
        Page page= pageForm.getPage();
        Map map= pageForm.getForm();
        QueryWrapper queryWrapper=MybatisPlusUtil.allLike(map,true);
        page=(Page)inventoryDao.selectPage(page,queryWrapper);
        return page;
    }

    @Override
    public List<Inventory> getList(Param param){
        QueryWrapper queryWrapper=MybatisPlusUtil.allLike(param.getParam(),true);
        return inventoryDao.selectList(queryWrapper);
    }

    //添加
    @Override
    public void insertInventory(Inventory inventory) {
        inventory.setUpdateTime(new Date());
        BigDecimal price=inventory.getPrice();
        Integer num=inventory.getNumber();
        if(price!=null && num!=null) {
            BigDecimal amount = price.multiply(new BigDecimal(num));
            inventory.setAmount(amount);
        }
        inventoryDao.insertOne(inventory);
    }

    //修改
    @Override
    public void updateInventory(Inventory inventory) {
        inventory.setUpdateTime(new Date());
        BigDecimal price=inventory.getPrice();
        Integer num=inventory.getNumber();
        if(price!=null && num!=null) {
            BigDecimal amount = price.multiply(new BigDecimal(num));
            inventory.setAmount(amount);
        }
        inventoryDao.updateById(inventory);
    }

    //删除
    @Override
    public void deleteInventory(Long id) {
        Inventory inventory=new Inventory();
        inventory.setId(id);
        inventory.setStatus("1");
        inventoryDao.updateById(inventory);
    }

    //导入数据
    @Override
    public void importList(List<Inventory> list) {
        if(list.size()==0){
            return;
        }
        Date date=new Date();
        List<Inventory> insertList=new ArrayList<>();
        List<Inventory> updateList=new ArrayList<>();
        for(Inventory inventory:list){
            if(inventory.getId()!=null){
                Inventory i=(Inventory) inventoryDao.selectById(inventory.getId());
                if(i!=null && !inventory.equals(i)){ //id查找的数据存在，且有更改
                    inventory.setUpdateTime(date);
                    updateList.add(inventory);
                }
            }else{
                inventory.setUpdateTime(date);
                insertList.add(inventory);
            }
        }
        if(insertList.size()!=0) {
            inventoryDao.insertList(insertList);
        }
        for(Inventory inventory:updateList){
            inventoryDao.updateById(inventory);
        }
    }

    //通过id获取
    @Override
    public Inventory getById(Long id) {
        Inventory inventory=(Inventory)inventoryDao.selectById(id);
        return inventory;
    }

    //APi------------------------------------------------------------------------------------------

    @Override
    public int getNumById(Long id){
        return inventoryDao.getNumById(id);
    }


}
