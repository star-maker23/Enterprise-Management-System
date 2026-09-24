package com.ems.domain.system.asset.query;

import cn.hutool.core.util.StrUtil;
import com.ems.common.core.page.AbstractPageQuery;
import com.ems.domain.system.asset.db.SysAssetEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AssetQuery extends AbstractPageQuery<SysAssetEntity> {

    private String assetName;
    private String assetCode;
    private String assetType;
    private String status;
    private String owner;

    @Override
    public QueryWrapper<SysAssetEntity> addQueryCondition() {
        QueryWrapper<SysAssetEntity> queryWrapper = new QueryWrapper<SysAssetEntity>()
                .like(StrUtil.isNotEmpty(assetName), "asset_name", assetName)
                .eq(StrUtil.isNotEmpty(assetCode), "asset_code", assetCode)
                .eq(StrUtil.isNotEmpty(assetType), "asset_type", assetType)
                .eq(StrUtil.isNotEmpty(status), "status", status)
                .like(StrUtil.isNotEmpty(owner), "owner", owner);

        this.setTimeRangeColumn("create_time");

        return queryWrapper;
    }
}