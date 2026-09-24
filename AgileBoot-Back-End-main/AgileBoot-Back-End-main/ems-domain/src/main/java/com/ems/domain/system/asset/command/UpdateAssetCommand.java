package com.ems.domain.system.asset.command;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateAssetCommand extends AddAssetCommand {

    @NotNull(message = "资产ID不能为空")
    @Positive
    private Long assetId;
}