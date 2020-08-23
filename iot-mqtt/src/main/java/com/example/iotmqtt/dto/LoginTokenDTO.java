package com.example.iotmqtt.dto;

import lombok.Data;

/**
 * @author lsw
 */
@Data
public class LoginTokenDTO {
    Boolean success;
    String message;
    Integer code;
    Long timestamp;
    DataResult result;

    @Override
    public String toString() {
        return "LoginTokenDTO{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", timestamp=" + timestamp +
                ", result=" + result +
                '}';
    }
}
