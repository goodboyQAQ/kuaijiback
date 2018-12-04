package org.wang.kuaijiback.controller;


import ch.qos.logback.core.encoder.EchoEncoder;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wang.kuaijiback.bean.Company;
import org.wang.kuaijiback.bean.Page;
import org.wang.kuaijiback.bean.Result;
import org.wang.kuaijiback.service.CompanyService;
import org.wang.kuaijiback.util.FileUtil;
import org.wang.kuaijiback.util.PoiUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/company")
@Slf4j
@Api(value="SHOP")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private PoiUtil poiUtil;


    @RequestMapping(value="/getcompany",method=RequestMethod.POST)
    public Result getCompany(@RequestBody Page<Company> p){
        Result result=new Result();
        try {
            PageInfo<Company> page = companyService.getCompany(p);
            result.setData(page);
            result.setMsg("查询成功");
            result.setSuccess(true);
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
        return result;
    }

    @RequestMapping(value="/addcompany",method=RequestMethod.POST)
    public Result addCompany(@RequestBody Company company){
        Result result=new Result();
        try {
            companyService.addCompany(company);
            result.setSuccess(true);
            result.setMsg("添加成功");
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return result;
    }

    @RequestMapping(value="/updatecompany",method=RequestMethod.POST)
    public  Result updateCompany(@RequestBody Company company){
        Result result=new Result();
        try{
            companyService.updateCompany(company);
            result.setSuccess(true);
            result.setMsg("更改成功");
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
        return result;
    }

    @RequestMapping(value="/deletecompany",method=RequestMethod.POST)
    public  Result deleteCompany(@RequestParam String id){
        Result result=new Result();
        try{
            companyService.deleteCompany(id);
            result.setSuccess(true);
            result.setMsg("删除成功");
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
        return result;
    }

    @RequestMapping(value="temp",method=RequestMethod.GET)
    public void temp(HttpServletResponse response){
        String fileName="company.xlsx";
        try{
            InputStream is=this.getClass().getResourceAsStream("/templates/company.xlsx");
            fileUtil.download(is,fileName,response);
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
    }

    @RequestMapping(value="upload",method=RequestMethod.POST)
    public Result uplaod(@RequestParam("file")MultipartFile file){
        Result result=new Result();
        try{
            Workbook wb=poiUtil.getWorkBook(file);
            List<Company> list=poiUtil.importExcel(wb,Company.class); //解析导入的数据
            companyService.importCompany(list);  //存入数据库
            result.setMsg("导入成功");
            result.setSuccess(true);
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
        return result;
    }

    @RequestMapping(value="download",method=RequestMethod.GET)
    public Result download(Company company,HttpServletResponse response){
        Result result=new Result();
        try{
            //查询数据
            List<Company> list=companyService.exportData(company);
            if(list.size()==0){
                result.setMsg("数据为空");
                return result;
            }
            String fileName="company.xlsx";
            InputStream is=this.getClass().getResourceAsStream("/templates/company.xlsx");
            poiUtil.exportData(fileName,list,response,Company.class);
            result.setSuccess(true);
            result.setMsg("导出成功");
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
        return result;

    }

    @RequestMapping(value="getcompanybyid",method=RequestMethod.GET)
    public Result getCompanyById(@RequestParam String id){
        Result result=new Result();
        try {
            Company company=companyService.getCompanyById(id);
            if(company!=null){
                result.setSuccess(true);
                result.setMsg("查询成功");
                result.setData(company);
            }
            result.setMsg("查无此条数据");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return result;
    }








}
