package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/***
 * @Auther: 20203
 * @ClassName: Article
 * @Date: 2019/8/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
  private String id;
  private String title;
  private String insert_img;
  private String content;
  private String status;
  private Date up_date;
  private String guruid;
}
