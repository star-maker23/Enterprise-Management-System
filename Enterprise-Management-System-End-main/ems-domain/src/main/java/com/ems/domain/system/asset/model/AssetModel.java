package com.ems.domain.system.asset.model;

import cn.hutool.core.bean.BeanUtil;
import com.ems.common.exception.ApiException;
import com.ems.common.exception.error.ErrorCode;
import com.ems.domain.system.asset.command.AddAssetCommand;
import com.ems.domain.system.asset.command.UpdateAssetCommand;
import com.ems.domain.system.asset.db.SysAssetEntity;
import com.ems.domain.system.asset.db.SysAssetService;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AssetModel extends SysAssetEntity {

    private SysAssetService assetService;

    public AssetModel(SysAssetService assetService) {
        this.assetService = assetService;
    }

    public AssetModel(SysAssetEntity entity, SysAssetService assetService) {
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.assetService = assetService;
    }

    public void loadFromAddCommand(AddAssetCommand addCommand) {
        if (addCommand != null) {
            BeanUtil.copyProperties(addCommand, this, "assetId");
        }
    }

    public void loadFromUpdateCommand(UpdateAssetCommand command) {
        if (command != null) {
            loadFromAddCommand(command);
        }
    }

    public void checkAssetNameUnique() {
        if (assetService.isAssetNameDuplicated(getAssetId(), getAssetName())) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, getAssetName(), "资产名称");
        }
    }

    public void checkAssetCodeUnique() {
        if (assetService.isAssetCodeDuplicated(getAssetId(), getAssetCode())) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, getAssetCode(), "资产编码");
        }
    }
}