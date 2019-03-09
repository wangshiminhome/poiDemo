package com.wsm.poidemo.poi.service.impl;


import com.wsm.poidemo.poi.service.PersonService;
import com.wsm.poidemo.poi.utils.FileUtils;
import com.wsm.poidemo.poi.vo.PersonVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceimpl implements PersonService {
    /**
     * 注册url
     */
    private static final String SUFFIX_2003 = ".xls";
    private static final String SUFFIX_2007 = ".xlsx";
    private static final String separator = File.separator;

    private String txtFilePath="";
    private StringBuffer sb;

    //对应人数
    private Integer findCount;
    //未对应人员
    private Integer notFindCount;
    @Override
    public String resolveExcel(MultipartFile file) throws Exception {
        sb = new StringBuffer();
        findCount=0;
        notFindCount=0;
        List<PersonVO> personVOList = new ArrayList<>();
        if (file == null) {
            throw new RuntimeException("上传文件不能为空");
        }
        //获取文件的名字
        String originalFilename = file.getOriginalFilename();
        if("person.xlsx".equals(originalFilename)||"person.xls".equals(originalFilename)){}else{
            throw new RuntimeException("请上传正确的文件格式");
        }
        Workbook workbook = null;
        try {
            if (originalFilename.endsWith(SUFFIX_2003)) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (originalFilename.endsWith(SUFFIX_2007)) {
                workbook = new XSSFWorkbook(file.getInputStream());

            }
        } catch (Exception e) {
            System.out.println(originalFilename);
            e.printStackTrace();
            throw new RuntimeException("格式错误");
        }
        if (workbook == null) {
            System.out.println(originalFilename);
            throw new RuntimeException("格式错误");
        } else {
            //获取所有的工作表的的数量
            int numOfSheet = workbook.getNumberOfSheets();
            //遍历这个这些表
            for (int i = 0; i < numOfSheet; i++) {
                //获取一个sheet也就是一个工作簿
                Sheet sheet = workbook.getSheetAt(i);
                int lastRowNum = sheet.getLastRowNum();
                //从第一行开始第一行一般是标题
                for (int j = 1; j <= lastRowNum; j++) {
                    Row row = sheet.getRow(j);
                    PersonVO personVO = new PersonVO();
                    //获取personNo
                    if (row.getCell(0) != null) {
                        row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                        String personNo = row.getCell(0).getStringCellValue();
                        personVO.setPersonNo(personNo);
                    }
                    //personName
                    if (row.getCell(1) != null) {
                        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                        String personName = row.getCell(1).getStringCellValue();
                        personVO.setPersonName(personName);
                    }
                    //post
                    if (row.getCell(2) != null) {
                        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                        String post = row.getCell(2).getStringCellValue();
                        personVO.setPost(post);
                    }
                    //idNumber
                    if (row.getCell(3) != null) {
                        row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                        String idNumber = row.getCell(3).getStringCellValue();
                        personVO.setIdNumber(idNumber);
                    }
                    personVOList.add(personVO);
                }
            }
        }
        //日志文件存放路径
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentTime = formatter.format(System.currentTimeMillis());// 当前时间作为文件名字
        txtFilePath = "D:" + separator + "summary" + separator + "personLog"+currentTime + ".txt";
        //生成日志TXT文件
        FileUtils.creatTxtFile(txtFilePath);

        for(PersonVO personvo:personVOList){
            String str ="插入数据:" + "personNo="+personvo.getPersonNo() + "；personName=" + personvo.getPersonName() + "；post=" + personvo.getPost()  +
                        "；idNumber=" + personvo.getIdNumber();
            FileUtils.writeTxtFile(txtFilePath,str);
            sb.append(str + "</br>");
            notFindCount ++ ;
        }
        sb.append("更新人数："+findCount);
        sb.append("插入人数："+notFindCount);
        FileUtils.writeTxtFile(txtFilePath,"更新人数："+findCount);
        FileUtils.writeTxtFile(txtFilePath,"插入人数："+notFindCount);
        return sb.toString();
    }

    //修改
    public void update(PersonVO personvo) throws Exception {
        String str = "更新数据:" + "personNo="+personvo.getPersonNo() + "；personName=" + personvo.getPersonName() +         "；post=" + personvo.getPost()  +
                "；idNumber=" + personvo.getIdNumber();
        FileUtils.writeTxtFile(txtFilePath,str);
        sb.append(str + "</br>");
        findCount++;
    }
}
