package com.example.iotmqtt.sevice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.iotmqtt.dao.TFileDir;
import com.example.iotmqtt.util.Tree;
import com.example.iotmqtt.vo.FileDirVO;

import java.util.Collection;

public interface ITFileDirService extends IService<TFileDir> {

    /**
     * 根据客户端id获取目录(树状结构)
     * @param clientId
     * @return
     */
    Collection<FileDirVO> getFileDirVOByClientId(Long clientId);


}
