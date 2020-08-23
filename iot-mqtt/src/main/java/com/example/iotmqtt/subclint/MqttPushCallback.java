package com.example.iotmqtt.subclint;

import cn.hutool.json.JSONUtil;
import com.example.iotmqtt.dto.NewBlowerDTO;
import com.example.iotmqtt.server.WebSocketServer;
import com.example.iotmqtt.sevice.IYyPreDeviceService;
import com.example.iotmqtt.sevice.impl.YyPreDeviceServiceImpl;
import com.example.iotmqtt.util.SpringUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * MQTT 推送回调
 * @author wunaozai
 * @date 2018-08-22
 */
public class MqttPushCallback implements MqttCallback {

    private static final Logger log = LoggerFactory.getLogger(MqttPushCallback.class);

    private IYyPreDeviceService preDeviceService;
    MqttPushCallback(){
        this.preDeviceService = SpringUtils.getBean(YyPreDeviceServiceImpl.class);
    }

    @SneakyThrows
    @Override
    public void connectionLost(Throwable cause) {
        log.info("断开连接，建议重连" + this);
        //断开连接，建议重连
//        MqttPushClient.getInstance().getClient().reconnect();
//        MqttPushClient.getInstance().getClient().subscribe("/yysj/pro/#");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        //log.info(token.isComplete() + "");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        //log.info("Topic: " + topic);
//        log.info("Message: " + new String(message.getPayload()));
        NewBlowerDTO newBlowerDTO = JSONUtil.parse(new String(message.getPayload())).toBean(NewBlowerDTO.class);
        //websocket广播
        if ("wack".equals(newBlowerDTO.getTyp()) || "ieecfg".equals(newBlowerDTO.getCmd())){
            WebSocketServer.sendInfo(new String(message.getPayload()),null);
            log.info("Topic:{},指令返回数据：{}",topic,message);
        }
        if ("rprt".equals(newBlowerDTO.getTyp())){
            WebSocketServer.sendInfo(new String(message.getPayload()),null);
            log.info("Topic:{},上报数据：{}",topic,message);
        }
        //发送的指令
        if (topic.startsWith("/yysj/pro/sub/sblw/")){
            log.info("Topic:{},发送的指令为：{}",topic,message);
            WebSocketServer.sendInfo(new String(message.getPayload()),null);
        }

        //更新设备在线状态
        try {
            if (!"wack".equals(newBlowerDTO.getTyp())){
                Boolean isSuccess = preDeviceService.udpateReportTime(newBlowerDTO.getExt().getIe());
                log.info("更新设备在线状态：Topic:{},res:{}",topic,isSuccess);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("更新设备在线状态时出错，错误信息为：",e.getMessage());
        }

        if ("stts".equals(newBlowerDTO.getCmd())){
            log.info("Topic:{},Message: {}",topic,newBlowerDTO.toString());
            /*
            调用basic修改设备信息修改为本地直接修改
            if (newBlowerDTO.getExt().getIe().startsWith("YH")){
                NotifyBasic.getInstance().updatePreDevice(newBlowerDTO);
            }
            */
            try {
                Boolean isSuccess = preDeviceService.updatePreDeviceInfo(newBlowerDTO);
                log.info("更新设备信息结果：{}",isSuccess);
            }catch (Exception e){
                e.printStackTrace();
                log.error("更新设备信息时出错，错误信息为：",e.getMessage());
            }
        }
    }

}
