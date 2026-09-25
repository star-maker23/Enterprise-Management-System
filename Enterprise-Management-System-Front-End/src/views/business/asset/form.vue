<script setup lang="ts">
import { ref } from "vue";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";
import { useUserStoreHook } from "@/store/modules/user";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
    assetName: "",
    assetCode: "",
    assetType: "",
    status: "",
    owner: "",
    purchaseDate: "",
    price: undefined,
    remark: ""
  })
});

const formData = ref(props.formInline);

const formRuleRef = ref();

function getFormRuleRef() {
  return formRuleRef.value;
}

defineExpose({ getFormRuleRef });
</script>

<template>
  <el-form
    ref="formRuleRef"
    :model="formData"
    :rules="formRules"
    label-width="82px"
  >
    <el-form-item label="资产名称" prop="assetName">
      <el-input
        v-model="formData.assetName"
        clearable
        placeholder="请输入资产名称"
      />
    </el-form-item>

    <el-form-item label="资产编码" prop="assetCode">
      <el-input
        v-model="formData.assetCode"
        clearable
        placeholder="请输入资产编码"
      />
    </el-form-item>

    <el-form-item label="资产类型" prop="assetType">
      <el-select
        v-model="formData.assetType"
        placeholder="请选择类型"
        clearable
        class="!w-[180px]"
      >
        <el-option
          v-for="dict in useUserStoreHook().dictionaryList['sysAsset.assetType']"
          :key="dict.value"
          :label="dict.label"
          :value="dict.value"
        />
      </el-select>
    </el-form-item>

    <el-form-item label="状态" prop="status">
      <el-select
        v-model="formData.status"
        placeholder="请选择状态"
        clearable
        class="!w-[180px]"
      >
        <el-option
          v-for="dict in useUserStoreHook().dictionaryList['sysAsset.status']"
          :key="dict.value"
          :label="dict.label"
          :value="dict.value"
        />
      </el-select>
    </el-form-item>

    <el-form-item label="负责人" prop="owner">
      <el-input
        v-model="formData.owner"
        clearable
        placeholder="请输入负责人"
      />
    </el-form-item>

    <el-form-item label="购入日期" prop="purchaseDate">
      <el-date-picker
        v-model="formData.purchaseDate"
        type="date"
        placeholder="请选择购入日期"
        value-format="YYYY-MM-DD"
      />
    </el-form-item>

    <el-form-item label="价格" prop="price">
      <el-input-number
        v-model="formData.price"
        :min="0"
        :precision="2"
        placeholder="请输入价格"
      />
    </el-form-item>

    <el-form-item label="备注" prop="remark">
      <el-input
        v-model="formData.remark"
        clearable
        placeholder="请输入备注"
        rows="4"
        type="textarea"
      />
    </el-form-item>
  </el-form>
</template>