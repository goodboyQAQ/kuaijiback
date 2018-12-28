package org.wang.kuaijiback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wang.kuaijiback.bean.*;
import org.wang.kuaijiback.dao.InventoryDao;
import org.wang.kuaijiback.exception.NumException;
import org.wang.kuaijiback.service.InventoryActionService;
import org.wang.kuaijiback.util.FileUtil;
import org.wang.kuaijiback.util.PoiUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/inventoryaction")
@Slf4j
public class InventoryActionController {

    @Autowired
    private InventoryActionService inventoryActionService;

    @Autowired
    private InventoryDao inventoryDao;

    //分页查询
    @RequestMapping(value="/get",method=RequestMethod.POST)
    public Result getPage(@RequestBody PageForm pageForm){
        Result result=new Result();
        try {
            Page page=inventoryActionService.getPage(pageForm);
            result.ok("查询成功",page);
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
        return result;
    }

    //添加
    @RequestMapping(value="/insert",method=RequestMethod.POST)
    public Result add(@RequestBody InventoryAction inventoryAction){
        Result result=new Result();
        try {
            inventoryActionService.insertInventoryAction(inventoryAction);
            result.ok("添加成功");
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return result;
    }

    @RequestMapping(value="/update",method=RequestMethod.POST)
    public  Result update(@RequestBody InventoryAction inventoryAction){
        Result result=new Result();
        try{
            inventoryActionService.updateInventoryAction(inventoryAction);
            result.ok("更改成功");
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
        return result;
    }

    @RequestMapping(value="/delete",method=RequestMethod.POST)
    public  Result delete(@RequestParam Long id){
        Result result=new Result();
        try{
            inventoryActionService.deleteInventoryAction(id);
            result.ok("删除成功");
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
        return result;
    }

    @RequestMapping(value="temp",method=RequestMethod.GET)
    public void temp(HttpServletResponse response){
        String fileName="inventoryAction.xlsx";
        try{
            InputStream is=this.getClass().getResourceAsStream("/templates/inventoryAction.xlsx");
            FileUtil.download(is,fileName,response);
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
    }

    @RequestMapping(value="upload",method=RequestMethod.POST)
    public Result uplaod(@RequestParam("file")MultipartFile file){
        Result result=new Result();
        try{
            Workbook wb=PoiUtil.getWorkBook(file);
            List<InventoryAction> list=PoiUtil.importExcel(wb,InventoryAction.class); //解析导入的数据
            inventoryActionService.importList(list);  //存入数据库
            result.ok("导入成功");
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
        return result;
    }

    @RequestMapping(value="download",method=RequestMethod.GET)
    public Result download(Param param, HttpServletResponse response){
        Result result=new Result();
        try{
            //查询数据
            List<InventoryAction> list=inventoryActionService.getList(param);
            if(list.size()==0){
                result.setMsg("数据为空");
                return result;
            }
            String fileName="inventoryAction.xlsx";
            InputStream is=this.getClass().getResourceAsStream("/templates/inventoryAction.xlsx");
            PoiUtil.exportData(fileName,list,response,InventoryAction.class);
            result.ok("导出成功");
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
        return result;

    }

    @RequestMapping("/confirm")
    public void confirm(InventoryAction inventoryAction){
        try {
            inventoryActionService.confirm(inventoryAction);
        }catch(NumException e){
            log.error(e.getMessage(),e);
        }
    }

}
