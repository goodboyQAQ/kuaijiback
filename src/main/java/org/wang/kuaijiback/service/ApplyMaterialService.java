package org.wang.kuaijiback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.wang.kuaijiback.bean.ApplyMaterial;
import org.wang.kuaijiback.bean.PageForm;
import org.wang.kuaijiback.bean.Param;

import java.util.List;

public interface ApplyMaterialService {
    //分页
    Page<ApplyMaterial> getPage(PageForm pageForm);

    List<ApplyMaterial> getList(Param param);
    //增
    void insertApplyMaterial(ApplyMaterial applyMaterial);
    //改
    void updateApplyMaterial(ApplyMaterial applyMaterial);
    //删
    void deleteApplyMaterial(int id);
    //批量导入  同时更新与插入
    void importList(List<ApplyMaterial> list);

    ApplyMaterial getById(Integer id);
}
