package com.ems.admin.controller.system;

import com.ems.admin.customize.aop.accessLog.AccessLog;
import com.ems.common.core.base.BaseController;
import com.ems.common.core.dto.ResponseDTO;
import com.ems.common.core.page.PageDTO;
import com.ems.common.enums.common.BusinessTypeEnum;
import com.ems.domain.common.command.BulkOperationCommand;
import com.ems.domain.system.asset.AssetApplicationService;
import com.ems.domain.system.asset.command.AddAssetCommand;
import com.ems.domain.system.asset.command.UpdateAssetCommand;
import com.ems.domain.system.asset.dto.AssetDTO;
import com.ems.domain.system.asset.query.AssetQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "资产管理API", description = "资产相关的增删查改")
@RestController
@RequestMapping("/system/assets")
@Validated
@RequiredArgsConstructor
public class SysAssetController extends BaseController {

    private final AssetApplicationService assetApplicationService;

    @Operation(summary = "资产列表")
    @PreAuthorize("@permission.has('system:asset:list')")
    @GetMapping
    public ResponseDTO<PageDTO<AssetDTO>> list(AssetQuery query) {
        PageDTO<AssetDTO> pageDTO = assetApplicationService.getAssetList(query);
        return ResponseDTO.ok(pageDTO);
    }

    @Operation(summary = "添加资产")
    @PreAuthorize("@permission.has('system:asset:add')")
    @AccessLog(title = "资产管理", businessType = BusinessTypeEnum.ADD)
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody @Validated AddAssetCommand addCommand) {
        assetApplicationService.addAsset(addCommand);
        return ResponseDTO.ok();
    }

    @Operation(summary = "修改资产")
    @PreAuthorize("@permission.has('system:asset:edit')")
    @AccessLog(title = "资产管理", businessType = BusinessTypeEnum.MODIFY)
    @PutMapping("/{assetId}")
    public ResponseDTO<Void> edit(@PathVariable Long assetId, @RequestBody @Validated UpdateAssetCommand updateCommand) {
        updateCommand.setAssetId(assetId);
        assetApplicationService.updateAsset(updateCommand);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除资产")
    @PreAuthorize("@permission.has('system:asset:remove')")
    @AccessLog(title = "资产管理", businessType = BusinessTypeEnum.DELETE)
    @DeleteMapping
    public ResponseDTO<Void> remove(@RequestParam("assetIds") @NotNull @NotEmpty List<Long> assetIds) {
        assetApplicationService.deleteAsset(new BulkOperationCommand<>(assetIds));
        return ResponseDTO.ok();
    }
}