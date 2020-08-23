package com.example.iotmqtt.dto;

import lombok.Data;

/**
 * @author lsw
 * 调用basic返回格式
 */
@Data
public class BasicResponseDTO {
    private Long timestamp;
    private String status;
    private String code;
    private String error;
    private String message;
    private String path;
    private Boolean success;
}
