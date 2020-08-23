package com.example.iotmqtt.sevice.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.iotmqtt.dto.NewBlowerDTO;
import com.example.iotmqtt.dto.YyPreDeviceDTO;
import com.example.iotmqtt.entity.YyPreDevice;
import com.example.iotmqtt.mapper.YyPreDeviceMapper;
import com.example.iotmqtt.sevice.IYyPreDeviceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
@Service
@Slf4j
public class YyPreDeviceServiceImpl extends ServiceImpl<YyPreDeviceMapper, YyPreDevice> implements IYyPreDeviceService {

    private final YyPreDeviceMapper preDeviceMapper;

    @Override
    public IPage getPreDevicePage(YyPreDeviceDTO preDeviceDTO) {
        if(preDeviceDTO == null){
            log.error("设备对象不能为空");
            throw  new NullPointerException("设备对象不能为空");
        }
        IPage page = new Page();
        if (ObjectUtil.isNull(preDeviceDTO.getCurrent())){
            preDeviceDTO.setCurrent(1);
        }
        if (ObjectUtil.isNull(preDeviceDTO.getSize())){
            preDeviceDTO.setSize(10);
        }
        page.setCurrent(preDeviceDTO.getCurrent());
        page.setSize(preDeviceDTO.getSize());
        YyPreDevice preDevice = new YyPreDevice();
        BeanUtil.copyProperties(preDeviceDTO,preDevice);
        IPage page1 = this.page(page, Wrappers.<YyPreDevice>lambdaQuery()
                .eq(YyPreDevice::getHasDelete,0)
                .eq(YyPreDevice::getPassType,0)
                .orderByDesc(YyPreDevice::getCreateTime));
        return page1;
    }

    @Override
    public Boolean updatePreDeviceInfo(NewBlowerDTO newBlowerDTO) {
        String deviceSn = newBlowerDTO.getExt().getIe();
        String ver = newBlowerDTO.getVer();
        String cmd = newBlowerDTO.getCmd();
        String typ = newBlowerDTO.getTyp();
        String dvr = newBlowerDTO.getExt().getDvr();
        Integer dtp = newBlowerDTO.getExt().getDtp();
        String deviceName = newBlowerDTO.getExt().getId();
        String tm = newBlowerDTO.getExt().getTm();
        Integer sn = newBlowerDTO.getExt().getSn();
        String ic = newBlowerDTO.getExt().getIc();
        Integer csq = newBlowerDTO.getExt().getCsq();
        Long rt = newBlowerDTO.getExt().getRt();
        Integer pccnt = newBlowerDTO.getExt().getPccnt();

        YyPreDevice preDevice = new YyPreDevice();
        preDevice.setDeviceDbm(csq.doubleValue());
        preDevice.setDeviceName(deviceName);
        preDevice.setDeviceSn(deviceSn);
        preDevice.setDeviceIdentificationCode(deviceSn);
        preDevice.setDeviceType(dtp.toString());
        preDevice.setDeviceSequence(sn.toString());
        preDevice.setSimNumber(ic);
        preDevice.setHardwareVersion(dvr);
        preDevice.setUpdateTime(new Date(System.currentTimeMillis()));
        //在线
        preDevice.setOnline(1);
        preDevice.setUpdateTime(new Date(System.currentTimeMillis()));

        // 判断是否存在于测试设备列表中，存在更新，不存在插入
        List<YyPreDevice> preDeviceList = this.preDeviceMapper.selectList(Wrappers.<YyPreDevice>lambdaQuery()
                .eq(YyPreDevice::getDeviceSn,deviceSn));
        if (CollectionUtils.isEmpty(preDeviceList) && deviceSn.startsWith("YH")){
            log.info("the device not exist, insert:{}",preDevice);
            return this.preDeviceMapper.insert(preDevice)>0;
        }
        if (CollectionUtils.isNotEmpty(preDeviceList)){
            log.info("the device existed, update:{}",preDevice);
            return this.preDeviceMapper.update(preDevice,
                    Wrappers.<YyPreDevice>lambdaUpdate()
                            .eq(YyPreDevice::getDeviceSn,deviceSn)
                            .eq(YyPreDevice::getHasDelete,0)
                            .eq(YyPreDevice::getPassType,0)
            )>0;
        }
        return false;
    }

    @Override
    public Boolean udpateReportTime(String sn) {
        YyPreDevice preDevice = new YyPreDevice();
        preDevice.setUpdateTime(new Date(System.currentTimeMillis()));
        preDevice.setOnline(1);

        //更新
        Date offlineTime = DateUtils.addMinutes(new Date(System.currentTimeMillis()),-3);
        log.info("更新设备在线状态：sn={}：time = {}",sn,offlineTime);
        //更新刚上报数据的设备时间
        boolean res = this.update(preDevice,
                Wrappers.<YyPreDevice>lambdaQuery()
                .eq(YyPreDevice::getDeviceSn,sn)
                .eq(YyPreDevice::getHasDelete,0)
                .eq(YyPreDevice::getPassType,0)
        );
        YyPreDevice preDevice1 = new YyPreDevice();
        preDevice1.setOnline(0);
        //超过两分钟的设备设置为离线
        int nu = this.preDeviceMapper.update(preDevice1,
                Wrappers.<YyPreDevice>lambdaQuery()
                        .lt(YyPreDevice::getUpdateTime,offlineTime)
                        .eq(YyPreDevice::getHasDelete,0)
                        .eq(YyPreDevice::getPassType,0)
        );
        log.info("设置离线设备数量：{}",nu);
        return res;
    }

}
