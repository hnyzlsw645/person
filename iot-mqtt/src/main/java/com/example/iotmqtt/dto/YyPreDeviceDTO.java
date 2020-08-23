package com.example.iotmqtt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class YyPreDeviceDTO implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "设备自定义名称")
    private String deviceName;
    @ApiModelProperty(value = "设备序列号")
    private String deviceSequence;
    @ApiModelProperty(value = "设备sn")
    private String deviceSn;
    @ApiModelProperty(value = "修改前设备sn")
    private String oldSn;
    @ApiModelProperty(value = "设备类型")
    private String deviceType;
    @ApiModelProperty(value = "sim卡卡号")
    private String simNumber;
    @ApiModelProperty(value = "设备识别码")
    private String deviceIdentificationCode;
    @ApiModelProperty(value = "信号强度")
    private Double deviceDbm;
    @ApiModelProperty(value = "产品类型")
    private String productType;
    @ApiModelProperty(value = "软件版本")
    private String softVersion;
    @ApiModelProperty(value = "硬件版本")
    private String hardwareVersion;
    @ApiModelProperty(value = "上报类型")
    private String reportType;
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    @ApiModelProperty(value = "0：未删除，1：删除")
    private Integer hasDelete;
    @ApiModelProperty(value = "备注")
    private String remarks;
    @ApiModelProperty(value = "0：测试中，1：测试通过")
    private Integer passType;
    @ApiModelProperty(value = "0：离线， 1：在线")
    private Integer online;

    /**
     * 当前页码数
     */
    @ApiModelProperty(value = "当前页码数")
    private Integer current=1;
    /**
     * 当前记录数
     */
    @ApiModelProperty(value = "当前记录数")
    private Integer size=10;
}
