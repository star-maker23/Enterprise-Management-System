package com.ems.domain.system.asset.command;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class AddAssetCommand {

    @NotBlank(message = "资产名称不能为空")
    @Size(max = 128, message = "资产名称长度不能超过128个字符")
    private String assetName;

    @NotBlank(message = "资产编码不能为空")
    @Size(max = 64, message = "资产编码长度不能超过64个字符")
    private String assetCode;

    private String assetType;

    private String status;

    private String owner;

    private Date purchaseDate;

    private BigDecimal price;

    private String remark;
}