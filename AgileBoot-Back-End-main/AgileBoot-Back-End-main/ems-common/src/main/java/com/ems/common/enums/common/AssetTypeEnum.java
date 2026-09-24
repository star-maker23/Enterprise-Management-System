package com.ems.common.enums.common;

import com.ems.common.enums.DictionaryEnum;
import com.ems.common.enums.dictionary.CssTag;
import com.ems.common.enums.dictionary.Dictionary;

@Dictionary(name = "sysAsset.assetType")
public enum AssetTypeEnum implements DictionaryEnum<Integer> {

    ELECTRONIC(1, "电子设备", CssTag.PRIMARY),
    OFFICE(2, "办公设备", CssTag.SUCCESS),
    OTHER(3, "其他", CssTag.INFO);

    private final int value;
    private final String description;
    private final String cssTag;

    AssetTypeEnum(int value, String description, String cssTag) {
        this.value = value;
        this.description = description;
        this.cssTag = cssTag;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public String cssTag() {
        return cssTag;
    }
}