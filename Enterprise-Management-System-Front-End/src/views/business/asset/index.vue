<script setup lang="ts">
import { ref } from "vue";
import { useAssetHook } from "./utils/hook";
import { PureTableBar } from "@/components/RePureTableBar";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";

import Delete from "@iconify-icons/ep/delete";
import EditPen from "@iconify-icons/ep/edit-pen";
import Search from "@iconify-icons/ep/search";
import Refresh from "@iconify-icons/ep/refresh";
import AddFill from "@iconify-icons/ri/add-circle-line";
import { useUserStoreHook } from "@/store/modules/user";

/** !!!重要!!! 组件name最好和菜单表中的router_name一致, copy的时候记得更改这个名字*/
defineOptions({
  name: "BusinessAsset"
});

const assetTypeList = useUserStoreHook().dictionaryList["sysAsset.assetType"];
const assetStatusList = useUserStoreHook().dictionaryList["sysAsset.status"];

const tableRef = ref();

const searchFormRef = ref();
const {
  searchFormParams,
  pageLoading,
  columns,
  dataList,
  pagination,
  defaultSort,
  multipleSelection,
  onSearch,
  resetForm,
  openDialog,
  handleDelete,
  handleBulkDelete,
  getAssetList
} = useAssetHook();
</script>

<template>
  <div class="main">
    <!-- 搜索栏 -->
    <el-form
      ref="searchFormRef"
      :inline="true"
      :model="searchFormParams"
      class="search-form bg-bg_color w-[99/100] pl-8 pt-[12px]"
    >
      <el-form-item label="资产名称：" prop="assetName">
        <el-input
          v-model="searchFormParams.assetName"
          placeholder="请输入资产名称"
          clearable
          class="!w-[200px]"
        />
      </el-form-item>

      <el-form-item label="资产编码：" prop="assetCode">
        <el-input
          v-model="searchFormParams.assetCode"
          placeholder="请输入资产编码"
          clearable
          class="!w-[180px]"
        />
      </el-form-item>

      <el-form-item label="资产类型：" prop="assetType">
        <el-select
          v-model="searchFormParams.assetType"
          placeholder="请选择类型"
          clearable
          class="!w-[180px]"
        >
          <el-option
            v-for="dict in assetTypeList"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="状态：" prop="status">
        <el-select
          v-model="searchFormParams.status"
          placeholder="请选择状态"
          clearable
          class="!w-[180px]"
        >
          <el-option
            v-for="dict in assetStatusList"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="负责人：" prop="owner">
        <el-input
          v-model="searchFormParams.owner"
          placeholder="请输入负责人"
          clearable
          class="!w-[180px]"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          :icon="useRenderIcon(Search)"
          :loading="pageLoading"
          @click="onSearch"
        >
          搜索
        </el-button>
        <el-button
          :icon="useRenderIcon(Refresh)"
          @click="resetForm(searchFormRef, tableRef)"
        >
          重置
        </el-button>
      </el-form-item>
    </el-form>

    <!-- table bar 包裹  table -->
    <PureTableBar title="资产列表" :columns="columns" @refresh="onSearch">
      <!-- 表格操作栏 -->
      <template #buttons>
        <el-button
          type="primary"
          :icon="useRenderIcon(AddFill)"
          @click="openDialog()"
        >
          添加资产
        </el-button>
        <el-button
          type="danger"
          :icon="useRenderIcon(Delete)"
          @click="handleBulkDelete(tableRef)"
        >
          批量删除
        </el-button>
      </template>
      <template v-slot="{ size, dynamicColumns }">
        <pure-table
          border
          ref="tableRef"
          align-whole="center"
          showOverflowTooltip
          table-layout="auto"
          :loading="pageLoading"
          :size="size"
          adaptive
          :data="dataList"
          :columns="dynamicColumns"
          :default-sort="defaultSort"
          :pagination="pagination"
          :paginationSmall="size === 'small' ? true : false"
          :header-cell-style="{
            background: 'var(--el-table-row-hover-bg-color)',
            color: 'var(--el-text-color-primary)'
          }"
          @page-size-change="getAssetList"
          @page-current-change="getAssetList"
          @sort-change="getAssetList"
          @selection-change="
            rows => (multipleSelection = rows.map(item => item.assetId))
          "
        >
          <template #operation="{ row }">
            <el-button
              class="reset-margin"
              link
              type="primary"
              :size="size"
              :icon="useRenderIcon(EditPen)"
              @click="openDialog('编辑', row)"
            >
              修改
            </el-button>
            <el-popconfirm
              :title="`是否确认删除编号为${row.assetId}的这条数据`"
              @confirm="handleDelete(row)"
            >
              <template #reference>
                <el-button
                  class="reset-margin"
                  link
                  type="danger"
                  :size="size"
                  :icon="useRenderIcon(Delete)"
                >
                  删除
                </el-button>
              </template>
            </el-popconfirm>
          </template>
        </pure-table>
      </template>
    </PureTableBar>
  </div>
</template>

<style scoped lang="scss">
:deep(.el-dropdown-menu__item i) {
  margin: 0;
}

.search-form {
  :deep(.el-form-item) {
    margin-bottom: 12px;
  }
}
</style>