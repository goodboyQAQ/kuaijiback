package org.wang.kuaijiback.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.wang.kuaijiback.annotation.ExcelTitle;
import javax.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.io.OutputStream;

import java.lang.reflect.Field;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class PoiUtil<T> {
    public  Workbook getWorkBook(MultipartFile file) throws Exception{
        String fileName = file.getOriginalFilename().toLowerCase();
        InputStream is = null;
        try {
            if (fileName.endsWith("xlsx")) {
                return new XSSFWorkbook(file.getInputStream());
            } else if (fileName.endsWith("xls")) {
                return new HSSFWorkbook(file.getInputStream());
            } else {
                throw new Exception("excel文件类型错误");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception("文件格式错误");
        } finally {
            try {
                if (is != null) {
                    {
                        is.close();
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }


    /**
     *  导入
     * @param workbook  工作簿
     * @param clazz  对应实体类
     * @return
     */
    public List<T> importExcel(Workbook workbook, Class<T> clazz){
        String[] fields=clazz.getAnnotation(ExcelTitle.class).value();
        List<T> list=new ArrayList<>();  //返回的对象列表
        Sheet sheet=workbook.getSheetAt(0);
        for(Row row:sheet){
            //第一次循环 转表头
            if(row==sheet.getRow(0)){
                continue;
            }
            //第二次往后
            try {
                T t=clazz.newInstance();
                for(int i=0;i<row.getLastCellNum()+1;i++){
                    Cell cell=row.getCell(i);
                    if(cell!=null){
                        String cellValue=getCellStringVal(cell);
                        Field f=clazz.getDeclaredField(fields[i]);
                        f.setAccessible(true);
                        f.set(t,cellValue);
                    }
                }
                list.add(t);
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }
        }
        return list;
    }

    //导出
    public void exportData(String fileName,List<T> list, HttpServletResponse response,Class<T> clazz){
        Field[] field=clazz.getDeclaredFields();
        Workbook workbook=new XSSFWorkbook();
        Sheet sheet=workbook.createSheet();
        Row row=null;
        try {
            String[] title=clazz.getAnnotation(ExcelTitle.class).title();
            String[] fields=clazz.getAnnotation(ExcelTitle.class).value();
            for(int i=0;i<=list.size();i++){
                row=sheet.createRow(i);
                Cell cell=null;
                if(i==0){ //第一次创建标题行
                    for(int j=0;j<title.length;j++){
                        cell=row.createCell(j);
                        cell.setCellValue(title[j]);
                    }
                    continue;
                }
                T t = list.get(i - 1);
                for(int j=0;j<fields.length;j++){
                    Field f=t.getClass().getDeclaredField(fields[j]);
                    f.setAccessible(true);
                    cell=row.createCell(j);
                    if(f.get(t)!=null) {
                        cell.setCellValue(f.get(t).toString());
                    }
                }
            }
            //输出Excel文件
            OutputStream output=response.getOutputStream();
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("multipart/form-data");
            workbook.write(output);
            output.close();

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }

    private String getCellStringVal(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        switch (cellType) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue()); //日期型
                } else {
                    // 解决问题：1，科学计数法(如2.6E+10)，2，超长小数小数位不一致（如1091.19649281798读取出1091.1964928179796），3，整型变小数（如0读取出0.0）
                    return NumberToTextConverter.toText(cell.getNumericCellValue());
                }
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            case ERROR:
                return String.valueOf(cell.getErrorCellValue());
            default:
                return "";
        }
    }



}
