package com.example.iotmqtt.sevice.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.iotmqtt.dao.TFileDir;
import com.example.iotmqtt.entity.YyPreDevice;
import com.example.iotmqtt.mapper.TFileDirMapper;
import com.example.iotmqtt.mapper.YyPreDeviceMapper;
import com.example.iotmqtt.sevice.ITFileDirService;
import com.example.iotmqtt.util.Tree;
import com.example.iotmqtt.util.TreeUtil;
import com.example.iotmqtt.vo.FileDirVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class TFileDirServiceImpl extends ServiceImpl<TFileDirMapper,TFileDir> implements ITFileDirService {

    private final TFileDirMapper baseMapper;

    private final TreeUtil treeUtil = TreeUtil.getInstance();

    @Override
    public Collection<FileDirVO> getFileDirVOByClientId(Long clientId) {
        if (ObjectUtil.isNull(clientId)){
//            Log.error("getFileDirVOByClientId 没有传入clienId参数");
            return null;
        }
        LambdaQueryWrapper<TFileDir> queryCondition = Wrappers.<TFileDir>lambdaQuery()
                .eq(TFileDir::getClientId,clientId);

        List<TFileDir> fileList = baseMapper.selectList(queryCondition);
        if (CollectionUtils.isEmpty(fileList)){
//            Log.info("getFileDirVOByClientId未获取到目录集合");
            return null;
        }

        List<FileDirVO> fileDirVOS = new ArrayList<>(fileList.size());
        fileList.forEach(fileDirObj -> {
            FileDirVO vo = new FileDirVO();
            BeanUtil.copyProperties(fileDirObj,vo);
            fileDirVOS.add(vo);
        });

        Collection<? extends Tree> fileVOTree = treeUtil.getCompleteTrees(fileDirVOS);
        return (Collection<FileDirVO>) fileVOTree;
    }
}
