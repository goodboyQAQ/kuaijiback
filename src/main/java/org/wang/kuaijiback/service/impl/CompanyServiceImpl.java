package org.wang.kuaijiback.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.wang.kuaijiback.bean.Company;
import org.wang.kuaijiback.bean.Page;
import org.wang.kuaijiback.dao.CompanyDao;
import org.wang.kuaijiback.service.CompanyService;

import java.util.*;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Override
    public PageInfo<Company> getCompany(Page p){
        List<Company> companyList=null;
        PageInfo<Company> page=null;
        try {
            Company company=(Company)p.getBean();
            PageHelper.startPage(p.getPageNum(),p.getPageSize());
            companyList=companyDao.getCompany(company);
            page=new PageInfo<>(companyList);
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return page;
    }

    @Override
    public List<Company> exportData(Company company){
        return companyDao.getCompany(company);
    }

    @Override
    public void addCompany(Company company) {
        Date date=new Date();
        company.setCreateTime(date);
        company.setUpdateTime(date);
        companyDao.addCompany(company);
    }

    @Override
    public void deleteCompany(String id) {
        companyDao.deleteCompany(id);
    }

    @Override
    public void updateCompany(Company company) {
        Date date=new Date();
        company.setUpdateTime(date);
        companyDao.updateCompany(company);
    }

    @Override
    public void importCompany(List<Company> list) {
        if(list.size()==0){
            return;
        }
        Date date=new Date();
         List<Company> insertList=new ArrayList<>();
         List<Company> updateList=new ArrayList<>();
         for(Company company:list){
             if(StringUtil.isNotEmpty(company.getId())){
                 Company c=companyDao.getCompanyById(company.getId());
                 if(c!=null && !company.equals(c)){ //id查找的数据存在，且有更改
                    company.setUpdateTime(date);
                    updateList.add(company);
                 }
             }else{
                 company.setCreateTime(date);
                 company.setUpdateTime(date);
                 insertList.add(company);
             }
         }
         if(insertList.size()!=0) {
             companyDao.addCompanyList(insertList);
         }
         for(Company company:updateList){
             companyDao.updateCompany(company);
         }

    }

    @Override
    public Company getCompanyById(String id){
        Company company=companyDao.getCompanyById(id);
        return company;
    }

}
