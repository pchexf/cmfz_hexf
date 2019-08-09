package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/***
 * @ClassName: User
 * @Auther: 20203
 * @Date: 2019/8/1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Excel(name="id")
  private String id;
  @Excel(name="手机号")
  private String phone;
  @ExcelIgnore
  private String password;
  @ExcelIgnore
  private String salt;
  @Excel(name="照片",type = 2)
  private String pic_img;
  @Excel(name="法号")
  private String ahama;
  @Excel(name="姓名")
  private String name;
  @Excel(name="性别")
  private String sex;
  @Excel(name="城市")
  private String city;
  @Excel(name="签名")
  private String sign;
  @ExcelIgnore
  private String status;
  @Excel(name="日期")
  private Date reg_date;
  @ExcelIgnore
  private String guruld;
}
