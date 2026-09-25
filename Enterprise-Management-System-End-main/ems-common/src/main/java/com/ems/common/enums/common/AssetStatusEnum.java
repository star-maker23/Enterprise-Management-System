package com.ems.common.enums.common;

import com.ems.common.enums.DictionaryEnum;
import com.ems.common.enums.dictionary.CssTag;
import com.ems.common.enums.dictionary.Dictionary;


@Dictionary(name = "sysAsset.status")
public enum AssetStatusEnum implements DictionaryEnum<Integer> {

    IN_USE(1, "在用", CssTag.SUCCESS),
    IDLE(2, "闲置", CssTag.WARNING),
    REPAIRING(3, "维修中", CssTag.INFO),
    SCRAPPED(4, "报废", CssTag.DANGER);
    private final int value;
    private final String description;
    private final String cssTag;

    AssetStatusEnum(int value, String description, String cssTag) {
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