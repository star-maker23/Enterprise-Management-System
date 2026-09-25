package com.ems.domain.system.asset.db;

import com.baomidou.mybatisplus.extension.service.IService;

public interface SysAssetService extends IService<SysAssetEntity> {

    boolean isAssetNameDuplicated(Long assetId, String assetName);

    boolean isAssetCodeDuplicated(Long assetId, String assetCode);
}