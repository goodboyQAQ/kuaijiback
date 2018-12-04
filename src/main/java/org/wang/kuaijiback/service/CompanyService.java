package org.wang.kuaijiback.service;

import com.github.pagehelper.PageInfo;
import org.wang.kuaijiback.bean.Company;
import org.wang.kuaijiback.bean.Page;

import java.util.List;
import java.util.Map;

public interface CompanyService {
    PageInfo<Company> getCompany(Page p);
    List<Company> exportData(Company company);
    void addCompany(Company company);
    void deleteCompany(String id);
    void updateCompany(Company company);
    void importCompany(List<Company> list);
    Company getCompanyById(String id);
}
