package com.example.iotmqtt.dto;

import lombok.Data;

/**
 * @author lsw
 */
@Data
public class NewBlowerDTO {
    private String typ;
    private String cmd;
    private String ver;
    private NewBlowerExt ext;
}
