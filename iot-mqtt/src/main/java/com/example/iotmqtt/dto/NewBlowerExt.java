package com.example.iotmqtt.dto;

import lombok.Data;

/**
 * @author lsw
 */
@Data
public class NewBlowerExt {
    private String ie;
    private String ic;
    private Integer csq;
    private Integer dtp;
    private String dvr;
    private String id;
    private Integer sn;
    private Long rt;
    private Integer pccnt;
    private String tm;
    private NewBlowerData data;
}
