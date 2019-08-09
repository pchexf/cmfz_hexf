package com.baizhi;

import com.baizhi.entity.Student;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzHexfApplication.class)
public class CmfzHexfApplicationTests {
    @Autowired
    private UserService userService;
    @Test
    public void contextLoads() {
        //创建一个Excel文档
        Workbook workbook = new HSSFWorkbook();
        //创建一个工作薄
        Sheet sheet = workbook.createSheet("用户信息表");
        //创建一行
        Row row = sheet.createRow(0);
        //创建一个单元格
        Cell cell = row.createCell(0);
        //给单元格设置内容
        cell.setCellValue("这是人生");
        try {
            workbook.write(new FileOutputStream(new File("F://test.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void t1(){
        Student stu1 = new Student("1","小可爱",16,new Date());
        Student stu2 = new Student("2","小蛋黄",16,new Date());
        Student stu3 = new Student("3","小狗蛋",12,new Date());
        Student stu4 = new Student("4","小嘿嘿",10,new Date());
        Student stu5 = new Student("5","小小小",23,new Date());

        List<Student> users = Arrays.asList(stu1, stu2, stu3, stu4, stu5);
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("用户信息");
        Row row = sheet.createRow(0);
        String[] title = {"ID","名字","年龄","生日"};
        Cell cell = null;

        //构建字体
        Font font = workbook.createFont();
        font.setBold(true);    //加粗
        font.setColor(Font.COLOR_RED); //颜色
        font.setFontHeightInPoints((short)10);  //字号
        font.setFontName("楷体");  //字体
        font.setItalic(true);    //斜体
        font.setUnderline(FontFormatting.U_SINGLE);  //下划线

        //创建样式对象
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);     //将字体样式引入
        cellStyle.setAlignment(HorizontalAlignment.CENTER);  //文字居中
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);

            cell.setCellStyle(cellStyle);
        }
        //创建样式对象
        CellStyle cellStyle1 = workbook.createCellStyle();
        //创建日期对象
        DataFormat dataFormat = workbook.createDataFormat();
        //设置日期格式
        cellStyle1.setDataFormat(dataFormat.getFormat("yyy年MM月dd日"));
        for (int i = 0; i < users.size(); i++) {
            Row row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(users.get(i).getId());
            row1.createCell(1).setCellValue(users.get(i).getName());
            row1.createCell(2).setCellValue(users.get(i).getAge());
            row1.createCell(3).setCellValue(users.get(i).getBir());
            Cell cell1 = row1.createCell(3);
            cell1.setCellStyle(cellStyle1);
            cell1.setCellValue(users.get(i).getBir());
        }
        try {
            workbook.write(new FileOutputStream(new File("F://test.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testPoiImport() {

        try {
            //获取要导入的文件
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("F://test.xls")));

            //获取工作薄
            HSSFSheet sheet = workbook.getSheet("用户信息");

            for (int i = 1; i <=sheet.getLastRowNum(); i++) {

                Student student = new Student();

                //获取行
                HSSFRow row = sheet.getRow(i);

                //获取Id
                student.setId(row.getCell(0).getStringCellValue());
                //获取name
                student.setName(row.getCell(1).getStringCellValue());
                //获取age
                double ages = row.getCell(2).getNumericCellValue();
                student.setAge((int) ages);
                //获取生日
                student.setBir(row.getCell(3).getDateCellValue());

                //调用一个插入数据库的方法
                System.out.println(student);
            }

            //关闭资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test1(){
        User user = new User("1", "1", "1", "1", "1", "1", "1", "男", "1", "1", "1", new Date(), "1");
        userService.select(user);
        System.out.ptintln(user);
    }
}
