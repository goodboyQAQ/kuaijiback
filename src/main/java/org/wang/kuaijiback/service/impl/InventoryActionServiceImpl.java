package org.wang.kuaijiback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wang.kuaijiback.bean.Inventory;
import org.wang.kuaijiback.bean.InventoryAction;
import org.wang.kuaijiback.bean.PageForm;
import org.wang.kuaijiback.bean.Param;
import org.wang.kuaijiback.dao.InventoryActionDao;
import org.wang.kuaijiback.exception.NumException;
import org.wang.kuaijiback.service.InventoryActionService;
import org.wang.kuaijiback.service.InventoryService;
import org.wang.kuaijiback.util.MybatisPlusUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class InventoryActionServiceImpl implements InventoryActionService {

    @Autowired
    InventoryActionDao inventoryActionDao;

    @Autowired
    InventoryService inventoryService;

    //分页查询
    @Override
    public Page<InventoryAction> getPage(PageForm pageForm) {
        Page page= pageForm.getPage();
        Map map= pageForm.getForm();
        QueryWrapper queryWrapper=MybatisPlusUtil.allLike(map,true);
        page=(Page)inventoryActionDao.selectPage(page,queryWrapper);
        return page;
    }

    @Override
    public List<InventoryAction> getList(Param param){
        QueryWrapper queryWrapper=MybatisPlusUtil.allLike(param.getParam(),true);
        return inventoryActionDao.selectList(queryWrapper);
    }

    //添加
    @Override
    public void insertInventoryAction(InventoryAction inventoryAction) {
        inventoryAction.setActionTime(new Date());
        inventoryActionDao.insertOne(inventoryAction);
    }

    //修改
    @Override
    public void updateInventoryAction(InventoryAction inventoryAction) {
        inventoryActionDao.updateById(inventoryAction);
    }

    //删除
    @Override
    public void deleteInventoryAction(Long id) {
        InventoryAction inventoryAction=new InventoryAction();
        inventoryAction.setId(id);
        inventoryAction.setStatus("1");
        inventoryActionDao.updateById(inventoryAction);
    }

    //导入数据
    @Override
    public void importList(List<InventoryAction> list) {
        if(list.size()==0){
            return;
        }
        Date date=new Date();
        List<InventoryAction> insertList=new ArrayList<>();
        List<InventoryAction> updateList=new ArrayList<>();
        for(InventoryAction inventoryAction:list){
            if(inventoryAction.getId()!=null){
                InventoryAction i=(InventoryAction) inventoryActionDao.selectById(inventoryAction.getId());
                if(i!=null && !inventoryAction.equals(i)){ //id查找的数据存在，且有更改
                    inventoryAction.setActionTime(date);
                    updateList.add(inventoryAction);
                }
            }else{
                inventoryAction.setActionTime(date);
                insertList.add(inventoryAction);
            }
        }
        if(insertList.size()!=0) {
            inventoryActionDao.insertList(insertList);
        }
        for(InventoryAction InventoryAction:updateList){
            inventoryActionDao.updateById(InventoryAction);
        }
    }

    //通过id获取
    @Override
    public InventoryAction getById(Long id) {
        InventoryAction inventoryAction=(InventoryAction)inventoryActionDao.selectById(id);
        return inventoryAction;
    }

    //确认入库出库  改变库存表数据
    @Override
    public void confirm(InventoryAction inventoryAction) throws NumException{
        //1.获取当前库存数量
        int num=inventoryService.getNumById(inventoryAction.getInventoryId());
        //2.库存操作数量
        int changeNum=inventoryAction.getNumber();
        //3.更新库存数量
        String inOut=inventoryAction.getIn_out();
        Inventory inventory=new Inventory();
        inventory.setId(inventoryAction.getInventoryId());
        if(inOut.equals("0")){//入库
            num=num+changeNum;
            inventory.setNumber(num);
        }else if(inOut.equals("1")){ //出库
            num=num-changeNum;
            if(num<0){
                throw new NumException("库存不足");
            }
            inventory.setNumber(num);
        }
        inventoryService.updateInventory(inventory);
    }


}
