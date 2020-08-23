package com.example.iotmqtt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.iotmqtt.client.PushMessage;
import com.example.iotmqtt.client.SocketIOService;
import com.example.iotmqtt.dto.YyPreDeviceDTO;
import com.example.iotmqtt.server.WebSocketServer;
import com.example.iotmqtt.sevice.IYyPreDeviceService;
import com.example.iotmqtt.subclint.MqttPushClient;
import com.example.iotmqtt.util.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.WebServer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/preDevice")
@Api(value = "生产测试平台", tags = "生产测试")
public class YyPreDeviceController {

    @Autowired
    private final IYyPreDeviceService preDeviceService;

    @ApiOperation(value = "分页查询测试设备")
    @PostMapping("/getPreDevicePage")
    public ApiResult getPreDevicePage(@RequestBody YyPreDeviceDTO preDeviceDTO){
        try {
            IPage iPage = preDeviceService.getPreDevicePage(preDeviceDTO);
            return ApiResult.ok(iPage);
        }catch (Exception e){
            log.error("分页查询测试设备失败",e);
            return ApiResult.error(e);
        }
    }

    @ApiOperation(value = "mqtt消息发送")
    @PostMapping("/publishMqtt")
    public ApiResult publishMqtt(@RequestBody String json){
        try {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(json.getBytes());
            MqttPushClient.getInstance().getClient().publish("/yysj/pro/pub",mqttMessage);
            return ApiResult.ok();
        }catch (Exception e){
            log.error("推送主题失败",e);
            return ApiResult.error(e);
        }
    }


    @ApiOperation(value = "socket消息发送")
    @PostMapping("/publishSocketMsg")
    public ApiResult publishSocketMsg(@RequestBody String json){
        try {
            PushMessage msg = new PushMessage();
            msg.setLoginUserNum("88");
            msg.setContent("我推送成功了");
            WebSocketServer.sendInfo("","");
            return ApiResult.ok();
        }catch (Exception e){
            log.error("推送socket消息失败",e);
            return ApiResult.error(e);
        }
    }

}
