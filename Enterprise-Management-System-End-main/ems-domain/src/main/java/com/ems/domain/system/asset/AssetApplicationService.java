package com.ems.domain.system.asset;

import com.ems.common.core.page.PageDTO;
import com.ems.domain.common.command.BulkOperationCommand;
import com.ems.domain.system.asset.command.AddAssetCommand;
import com.ems.domain.system.asset.command.UpdateAssetCommand;
import com.ems.domain.system.asset.db.SysAssetEntity;
import com.ems.domain.system.asset.db.SysAssetService;
import com.ems.domain.system.asset.dto.AssetDTO;
import com.ems.domain.system.asset.model.AssetModel;
import com.ems.domain.system.asset.model.AssetModelFactory;
import com.ems.domain.system.asset.query.AssetQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetApplicationService {

    private final AssetModelFactory assetModelFactory;

    private final SysAssetService assetService;

    public PageDTO<AssetDTO> getAssetList(AssetQuery query) {
        Page<SysAssetEntity> page = assetService.page(query.toPage(), query.toQueryWrapper());
        List<AssetDTO> records = page.getRecords().stream().map(AssetDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public void addAsset(AddAssetCommand addCommand) {
        AssetModel assetModel = assetModelFactory.create();
        assetModel.loadFromAddCommand(addCommand);

        assetModel.checkAssetNameUnique();
        assetModel.checkAssetCodeUnique();

        assetModel.insert();
    }

    public void updateAsset(UpdateAssetCommand updateCommand) {
        AssetModel assetModel = assetModelFactory.loadById(updateCommand.getAssetId());
        assetModel.loadFromUpdateCommand(updateCommand);

        assetModel.checkAssetNameUnique();
        assetModel.checkAssetCodeUnique();

        assetModel.updateById();
    }

    public void deleteAsset(BulkOperationCommand<Long> deleteCommand) {
        assetService.removeBatchByIds(deleteCommand.getIds());
    }
}