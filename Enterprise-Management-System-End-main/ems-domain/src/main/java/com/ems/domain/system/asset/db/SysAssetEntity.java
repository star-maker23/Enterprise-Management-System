package com.ems.domain.system.asset.db;

import com.ems.common.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@TableName("sys_asset")
@ApiModel(value = "SysAssetEntity对象", description = "资产信息表")
public class SysAssetEntity extends BaseEntity<SysAssetEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("资产ID")
    @TableId(value = "asset_id", type = IdType.AUTO)
    private Long assetId;

    @ApiModelProperty("资产名称")
    @TableField("asset_name")
    private String assetName;

    @ApiModelProperty("资产编码")
    @TableField("asset_code")
    private String assetCode;

    @ApiModelProperty("资产类型")
    @TableField("asset_type")
    private String assetType;

    @ApiModelProperty("状态")
    @TableField("`status`")
    private String status;

    @ApiModelProperty("负责人")
    @TableField("owner")
    private String owner;

    @ApiModelProperty("购入日期")
    @TableField("purchase_date")
    private Date purchaseDate;

    @ApiModelProperty("价格")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("创建人名称")
    @TableField("creator_name")
    private String creatorName;

    @Override
    public Serializable pkVal() {
        return this.assetId;
    }
}