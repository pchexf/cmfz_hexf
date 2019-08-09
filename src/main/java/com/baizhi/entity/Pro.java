package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/***
 * @ClassName: Pro
 * @Auther: 20203
 * @Date: 2019/8/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pro {
    private String title;
    private List<City> citys;

}
