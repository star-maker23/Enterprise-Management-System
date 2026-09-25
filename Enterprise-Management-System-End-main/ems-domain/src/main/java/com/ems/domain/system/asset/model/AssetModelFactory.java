package com.ems.domain.system.asset.model;

import com.ems.common.exception.ApiException;
import com.ems.common.exception.error.ErrorCode.Business;
import com.ems.domain.system.asset.db.SysAssetEntity;
import com.ems.domain.system.asset.db.SysAssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssetModelFactory {

    private final SysAssetService assetService;

    public AssetModel loadById(Long assetId) {
        SysAssetEntity byId = assetService.getById(assetId);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, assetId, "资产");
        }
        return new AssetModel(byId, assetService);
    }

    public AssetModel create() {
        return new AssetModel(assetService);
    }
}