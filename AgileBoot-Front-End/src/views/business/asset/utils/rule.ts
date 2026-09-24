import { reactive } from "vue";
import type { FormRules } from "element-plus";

/** 自定义表单规则校验 */
export const formRules = reactive(<FormRules>{
  assetName: [
    { required: true, message: "资产名称为必填项", trigger: "blur" }
  ],
  assetCode: [
    { required: true, message: "资产编码为必填项", trigger: "blur" }
  ]
});