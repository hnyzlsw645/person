package com.example.iotmqtt.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.iotmqtt.client.PushMessage;
import com.example.iotmqtt.dto.YyPreDeviceDTO;
import com.example.iotmqtt.server.WebSocketServer;
import com.example.iotmqtt.sevice.ITFileDirService;
import com.example.iotmqtt.sevice.IYyPreDeviceService;
import com.example.iotmqtt.subclint.MqttPushClient;
import com.example.iotmqtt.util.ApiResult;
import com.example.iotmqtt.vo.FileDirVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/fileManage")
@Api(value = "文件管理", tags = "文件管理")
public class FileManageController {

    @Autowired
    private final ITFileDirService baseService = null;

    @ApiOperation(value = "根据clientId获取文件目录")
    @GetMapping("/getFileDirByClientId/{clientId}")
    public ApiResult publishSocketMsg(@PathVariable Long clientId){
        try {
            if (ObjectUtil.isNull(clientId)){
                return ApiResult.error("clientId不能为空");
            }
            Collection<FileDirVO> vo = baseService.getFileDirVOByClientId(clientId);
            return ApiResult.ok(vo);
        }catch (Exception e){
            log.error("获取目录结构失败",e);
            return ApiResult.error(e);
        }
    }

}
