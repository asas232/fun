package com.jackc.fun.pojo;

import lombok.Data;

import java.util.Comparator;
import java.util.Date;

/**
 * @Description
 * @Author chenjie
 * @DATE 2019/6/28 17:27
 */
@Data
public class Task {
    private Integer id;
    private Integer type;
    private String  content;
    private Long executeTime;
    private Integer  count;
    private Integer  runCount;
    private Integer  extensionTime;

}
