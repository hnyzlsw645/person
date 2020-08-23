package com.example.iotmqtt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.iotmqtt.entity.YyPreDevice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface YyPreDeviceMapper extends BaseMapper<YyPreDevice> {

}
