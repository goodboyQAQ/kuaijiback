package org.wang.kuaijiback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wang.kuaijiback.bean.ApplyMaterial;
import org.wang.kuaijiback.bean.PageForm;
import org.wang.kuaijiback.bean.Param;
import org.wang.kuaijiback.dao.ApplyMaterialDao;
import org.wang.kuaijiback.service.ApplyMaterialService;
import org.wang.kuaijiback.util.MybatisPlusUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ApplyMaterialServiceImpl implements ApplyMaterialService {

    @Autowired
    private ApplyMaterialDao applyMaterialDao;

    @Override
    public Page<ApplyMaterial> getPage(PageForm pageForm) {
        Page page= pageForm.getPage();
        Map map= pageForm.getForm();
        QueryWrapper queryWrapper=MybatisPlusUtil.allLike(map,true);
        page=(Page)applyMaterialDao.selectPage(page,queryWrapper);
        return page;
    }

    @Override
    public List<ApplyMaterial> getList(Param param) {
        QueryWrapper queryWrapper=MybatisPlusUtil.allLike(param.getParam(),true);
        return applyMaterialDao.selectList(queryWrapper);
    }

    @Override
    public void insertApplyMaterial(ApplyMaterial applyMaterial) {
        applyMaterial.setApplyTime(new Date());
        applyMaterialDao.insertOne(applyMaterial);
    }

    @Override
    public void updateApplyMaterial(ApplyMaterial applyMaterial) {
        applyMaterialDao.updateById(applyMaterial);
    }

    @Override
    public void deleteApplyMaterial(int id) {
        ApplyMaterial applyMaterial=new ApplyMaterial();
        applyMaterial.setId(id);
        applyMaterial.setStatus("1");
        applyMaterialDao.updateById(applyMaterial);
    }

    @Override
    public void importList(List<ApplyMaterial> list) {
        if(list.size()==0){
            return;
        }
        Date date=new Date();
        List<ApplyMaterial> insertList=new ArrayList<>();
        List<ApplyMaterial> updateList=new ArrayList<>();
        for(ApplyMaterial applyMaterial:list){
            if(applyMaterial.getId()!=null){
               ApplyMaterial a=(ApplyMaterial) applyMaterialDao.selectById(applyMaterial.getId());
                if(a!=null && !applyMaterial.equals(a)){ //id查找的数据存在，且有更改
                   applyMaterial.setApplyTime(date);
                   updateList.add(applyMaterial);
                }
            }else{
                applyMaterial.setApplyTime(date);
                insertList.add(applyMaterial);
            }
        }
        if(insertList.size()!=0) {
           applyMaterialDao.insertList(insertList);
        }
        for(ApplyMaterial applyMaterial:updateList){
           applyMaterialDao.updateById(applyMaterial);
        }
    }

    @Override
    public ApplyMaterial getById(Integer id) {
        return null;
    }
}
