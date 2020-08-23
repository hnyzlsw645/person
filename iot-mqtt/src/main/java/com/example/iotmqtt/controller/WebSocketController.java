package com.example.iotmqtt.controller;

import com.example.iotmqtt.server.WebSocketServer;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController("/iot-mqtt")
public class WebSocketController {
    //页面请求
    @GetMapping("/socket/{cid}")
    public String socket(@PathVariable String cid){
        return "连接成功";
    }

    //推送数据接口
    @ResponseBody
    @RequestMapping("/socket/push/{cid}")
    public String pushToWeb(@PathVariable String cid,String message){
        try {
            WebSocketServer.sendInfo(message,cid);
            WebSocketServer.sendInfo(message,null);
        }catch (IOException e){
            e.printStackTrace();
            return "推送失败";
        }
        return "发送成功";
    }



}
