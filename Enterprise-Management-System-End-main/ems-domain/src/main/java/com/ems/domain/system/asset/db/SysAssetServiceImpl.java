package com.ems.domain.system.asset.db;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysAssetServiceImpl extends ServiceImpl<SysAssetMapper, SysAssetEntity> implements SysAssetService {

    @Override
    public boolean isAssetNameDuplicated(Long assetId, String assetName) {
        QueryWrapper<SysAssetEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne(assetId != null, "asset_id", assetId)
                .eq("asset_name", assetName);
        return baseMapper.exists(queryWrapper);
    }

    @Override
    public boolean isAssetCodeDuplicated(Long assetId, String assetCode) {
        QueryWrapper<SysAssetEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne(assetId != null, "asset_id", assetId)
                .eq("asset_code", assetCode);
        return baseMapper.exists(queryWrapper);
    }
}