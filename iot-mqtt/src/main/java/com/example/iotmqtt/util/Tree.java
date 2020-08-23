package com.example.iotmqtt.util;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface Tree<T> {

    Long getId();

    void setId(Long id);

    Long getParentId();

    void setParentId(Long parentId);

    void setChild(List<T> list);

    void addChild(T tree);

}
