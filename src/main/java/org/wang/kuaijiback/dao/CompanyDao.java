package org.wang.kuaijiback.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.wang.kuaijiback.bean.Company;

import java.util.List;


@Mapper
public interface CompanyDao {

    List<Company> getCompany(Company company);

    void addCompany(Company company);

    void deleteCompany(String id);

    void updateCompany(Company company);

    void addCompanyList(List<Company> list);

    Company getCompanyById(String id);
}
