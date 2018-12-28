package org.wang.kuaijiback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wang.kuaijiback.bean.Inventory;
import org.wang.kuaijiback.bean.PageForm;
import org.wang.kuaijiback.bean.Param;
import org.wang.kuaijiback.bean.Result;
import org.wang.kuaijiback.service.InventoryService;
import org.wang.kuaijiback.util.FileUtil;
import org.wang.kuaijiback.util.PoiUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
@Slf4j
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;


    //分页查询
    @RequestMapping(value="/get",method=RequestMethod.POST)
    public Result getPage(@RequestBody PageForm pageForm){
        Result result=new Result();
        try {
            Page page=inventoryService.getPage(pageForm);
            result.ok("查询成功",page);
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
        return result;
    }

    //添加
    @RequestMapping(value="/insert",method=RequestMethod.POST)
    public Result insert(@RequestBody Inventory inventory){
        Result result=new Result();
        try {
            inventoryService.insertInventory(inventory);
            result.ok("添加成功");
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return result;
    }

    @RequestMapping(value="/update",method=RequestMethod.POST)
    public  Result update(@RequestBody Inventory inventory){
        Result result=new Result();
        try{
            inventoryService.updateInventory(inventory);
            result.setSuccess(true);
            result.setMsg("更改成功");
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
        return result;
    }

    @RequestMapping(value="/delete",method=RequestMethod.POST)
    public  Result delete(@RequestParam Long id){
        Result result=new Result();
        try{
            inventoryService.deleteInventory(id);
            result.setSuccess(true);
            result.setMsg("删除成功");
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
        return result;
    }

    @RequestMapping(value="/temp",method=RequestMethod.GET)
    public void temp(HttpServletResponse response){
        String fileName="inventory.xlsx";
        try{
            InputStream is=this.getClass().getResourceAsStream("/templates/inventory.xlsx");
            FileUtil.download(is,fileName,response);
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
    }

    @RequestMapping(value="/upload",method=RequestMethod.POST)
    public Result uplaod(@RequestParam("file")MultipartFile file){
        Result result=new Result();
        try{
            Workbook wb=PoiUtil.getWorkBook(file);
            List<Inventory> list=PoiUtil.importExcel(wb,Inventory.class); //解析导入的数据
            inventoryService.importList(list);  //存入数据库
            result.setMsg("导入成功");
            result.setSuccess(true);
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
        return result;
    }

    @RequestMapping(value="/download",method=RequestMethod.GET)
    public Result download(String name,String type, HttpServletResponse response){
        Map map=new HashMap();
        map.put("name",name);
        map.put("type",type);
        Param param=new Param();
        param.setParam(map);
        Result result=new Result();
        try{
            //查询数据
            List<Inventory> list=inventoryService.getList(param);
            if(list.size()==0){
                result.setMsg("数据为空");
                return result;
            }
            String fileName="inventory.xlsx";
            InputStream is=this.getClass().getResourceAsStream("/templates/inventory.xlsx");
            PoiUtil.exportData(fileName,list,response,Inventory.class);
            result.setSuccess(true);
            result.setMsg("导出成功");
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
        return result;

    }


}
