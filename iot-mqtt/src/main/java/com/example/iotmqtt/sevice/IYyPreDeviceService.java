package com.example.iotmqtt.sevice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.iotmqtt.dto.NewBlowerDTO;
import com.example.iotmqtt.dto.YyPreDeviceDTO;
import com.example.iotmqtt.entity.YyPreDevice;

public interface IYyPreDeviceService  extends IService<YyPreDevice> {

    /**
     * 分页查询设备列表
     * @param preDeviceDTO
     * @return
     */
    IPage getPreDevicePage(YyPreDeviceDTO preDeviceDTO);

    /**
     * 更新设备信息
     * @param newBlowerDTO
     * @return
     */
    Boolean updatePreDeviceInfo(NewBlowerDTO newBlowerDTO);

    /**
     * 更新上报时间
     * @param sn
     * @return
     */
    Boolean udpateReportTime(String sn);

}
