package com.example.iotmqtt.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("文件目录")
public class TFileDir {
    @ApiModelProperty("主键id")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("客户端id")
    private Long clientId;
    @ApiModelProperty("类型，1：目录，2：文件")
    private Integer type;
    @ApiModelProperty("父级id")
    private Long parentId;
    @ApiModelProperty("目录/文件名称")
    private String name;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("本记录创建时间")
    private Date createTime;
    @ApiModelProperty("文件创建时间")
    private Date fileTime;
    @ApiModelProperty("文件大小，单位K")
    private BigDecimal fileSize;
    @ApiModelProperty("文件信息")
    private String fileInfo;
    @ApiModelProperty("是否删除，0：未删除，1：删除")
    private Integer isDeleted;
    @ApiModelProperty("备注")
    private String remarks;
}
