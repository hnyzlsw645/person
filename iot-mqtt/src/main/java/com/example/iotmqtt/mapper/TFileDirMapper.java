package com.example.iotmqtt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.iotmqtt.dao.TFileDir;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TFileDirMapper extends BaseMapper<TFileDir> {
}
