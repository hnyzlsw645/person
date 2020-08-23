package com.example.iotmqtt.util;

import cn.hutool.json.JSONUtil;
import com.example.iotmqtt.dto.BasicResponseDTO;
import com.example.iotmqtt.dto.LoginTokenDTO;
import com.example.iotmqtt.dto.NewBlowerDTO;
import com.example.iotmqtt.subclint.MqttPushCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lsw
 */
public class NotifyBasic {
    private NotifyBasic(){};
    private static NotifyBasic notifyBasic = null;
    public static NotifyBasic getInstance(){
        if (notifyBasic == null){
            notifyBasic = new NotifyBasic();
        }
        return notifyBasic;
    }

    private static final Logger log = LoggerFactory.getLogger(NotifyBasic.class);

    private String token = null;

    public void updatePreDevice(NewBlowerDTO newBlowerDTO) throws Exception {
        Map header = new HashMap<String,String>();
        if (token == null){
            token = getToken();
        }
        header.put("Authorization",token);
//        String res = HttpUtil.sendHttpPost("http://60.205.246.102:8081/basic/preDevice/updatePreDeviceInfoByRemote",header,newBlowerDTO);
        String res = HttpUtil.sendHttpPost("http://localhost:8081/basic/preDevice/updatePreDeviceInfoByRemote",header,newBlowerDTO);
        if (JSONUtil.isJson(res)){
            BasicResponseDTO basicResponse = JSONUtil.parse(res).toBean(BasicResponseDTO.class);
            if (basicResponse.getSuccess() == null && !"success".equalsIgnoreCase(basicResponse.getMessage())){
                log.error("远程调用接口报错，错误信息：{}",res);
                //清空token重新调用
                if ("403".equalsIgnoreCase(basicResponse.getStatus())){
                    log.error("token可能过期了，已经重新获取token了");
                    token = null;
                    this.updatePreDevice(newBlowerDTO);
                }
            }
        }else {
            log.error("远程更新错误，错误信息为："+res);
        }
    }

    //获取token
    private String getToken() throws Exception {
        Map map = new HashMap<String,String>();
        //{"username":"admin","password":"123456","saveLogin":true}
        map.put("username","admin");
        map.put("password","123456");
        map.put("saveLogin",true);
//        String info = HttpUtil.sendHttpPost("http://60.205.246.102:8081/admin/login",map);
        String info = HttpUtil.sendHttpPost("http://localhost:8081/admin/login",map);
        if (JSONUtil.isJson(info)){
            LoginTokenDTO tokenDTO = JSONUtil.parse(info).toBean(LoginTokenDTO.class);
            log.info(tokenDTO.getResult().getToken());
            return tokenDTO.getResult().getToken();
        }
        return null;
    }
}
