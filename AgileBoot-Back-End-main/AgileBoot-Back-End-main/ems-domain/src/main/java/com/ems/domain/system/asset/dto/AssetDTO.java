package com.ems.domain.system.asset.dto;

import cn.hutool.core.bean.BeanUtil;
import com.ems.domain.system.asset.db.SysAssetEntity;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssetDTO {

    public AssetDTO(SysAssetEntity entity) {
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
    }

    private Long assetId;

    private String assetName;

    private String assetCode;

    private String assetType;

    private String status;

    private String owner;

    private Date purchaseDate;

    private BigDecimal price;

    private String remark;

    private String creatorName;

    private Date createTime;

    private Date updateTime;
}