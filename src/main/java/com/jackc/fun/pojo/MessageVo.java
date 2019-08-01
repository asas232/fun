package com.jackc.fun.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author chenjie
 * @DATE 2019/6/28 16:38
 */
@Data
public class MessageVo {
    private Date sendDate;
    private String from;
    private String subject;
}
