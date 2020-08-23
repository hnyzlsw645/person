package com.example.iotmqtt.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.iotmqtt.util.Tree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("文件目录")
public class FileDirVO implements Tree<FileDirVO> {
    @ApiModelProperty("主键id")
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
    @ApiModelProperty("文件创建时间")
    private Date fileTime;
    @ApiModelProperty("文件大小，单位K")
    private BigDecimal fileSize;
    @ApiModelProperty("是否删除，0：未删除，1：删除")
    private Integer isDeleted;
    private List<FileDirVO> child;

    @Override
    public void addChild(FileDirVO tree) {
        this.child.add(tree);
    }
}
