package com.example.iotmqtt.dto;

import lombok.Data;

/**
 * @author lsw
 * 新版放风机数据
 */
@Data
public class NewBlowerData {
    private Integer type;
    private Double at;
    private Double ah;
    private Integer psize;
    private Integer mode;
    private Integer ppos;
}
