import dayjs from "dayjs";
import editForm from "../form.vue";
import { message } from "@/utils/message";
import { AssetQuery, getAssetListApi } from "@/api/system/asset";
import {
  addAssetApi,
  updateAssetApi,
  deleteAssetApi,
  AssetRequest
} from "@/api/system/asset";
import { addDialog } from "@/components/ReDialog";
import { ElMessageBox, Sort } from "element-plus";
import { AddAssetRequest } from "../utils/types";
import { type PaginationProps } from "@pureadmin/table";
import { reactive, ref, onMounted, h, toRaw } from "vue";
import { useUserStoreHook } from "@/store/modules/user";
import { CommonUtils } from "@/utils/common";

const assetTypeMap = useUserStoreHook().dictionaryMap["sysAsset.assetType"];
const assetStatusMap = useUserStoreHook().dictionaryMap["sysAsset.status"];

export function useAssetHook() {
  const defaultSort: Sort = {
    prop: "createTime",
    order: "descending"
  };

  const pagination: PaginationProps = {
    total: 0,
    pageSize: 10,
    currentPage: 1,
    background: true
  };

  const searchFormParams = reactive<AssetQuery>({
    assetName: undefined,
    assetCode: undefined,
    assetType: undefined,
    status: undefined,
    owner: undefined,
    orderColumn: defaultSort.prop,
    orderDirection: defaultSort.order
  });

  const formRef = ref();
  const dataList = ref([]);
  const pageLoading = ref(true);
  const multipleSelection = ref([]);

  const columns: TableColumnList = [
    {
      type: "selection",
      align: "left"
    },
    {
      label: "资产编号",
      prop: "assetId",
      minWidth: 100
    },
    {
      label: "资产名称",
      prop: "assetName",
      minWidth: 120
    },
    {
      label: "资产编码",
      prop: "assetCode",
      minWidth: 120
    },
    {
      label: "资产类型",
      prop: "assetType",
      minWidth: 120,
      cellRenderer: ({ row, props }) => (
        <el-tag
          size={props.size}
          type={assetTypeMap?.[row.assetType]?.cssTag}
          effect="plain"
        >
          {assetTypeMap?.[row.assetType]?.label ?? row.assetType}
        </el-tag>
      )
    },
    {
      label: "状态",
      prop: "status",
      minWidth: 100,
      cellRenderer: ({ row, props }) => (
        <el-tag
          size={props.size}
          type={assetStatusMap?.[row.status]?.cssTag}
          effect="plain"
        >
          {assetStatusMap?.[row.status]?.label ?? row.status}
        </el-tag>
      )
    },
    {
      label: "负责人",
      prop: "owner",
      minWidth: 100
    },
    {
      label: "购入日期",
      prop: "purchaseDate",
      minWidth: 120
    },
    {
      label: "价格",
      prop: "price",
      minWidth: 100
    },
    {
      label: "备注",
      prop: "remark",
      minWidth: 150
    },
    {
      label: "创建时间",
      minWidth: 180,
      prop: "createTime",
      sortable: "custom",
      formatter: ({ createTime }) =>
        dayjs(createTime).format("YYYY-MM-DD HH:mm:ss")
    },
    {
      label: "操作",
      fixed: "right",
      width: 240,
      slot: "operation"
    }
  ];

  function onSearch() {
    // 点击搜索的时候 需要重置分页
    pagination.currentPage = 1;

    getAssetList();
  }

  function resetForm(formEl, tableRef) {
    if (!formEl) return;
    // 清空查询参数
    formEl.resetFields();
    // 清空排序
    searchFormParams.orderColumn = undefined;
    searchFormParams.orderDirection = undefined;
    tableRef.getTableRef().clearSort();
    // 重置分页并查询
    onSearch();
  }

  async function getAssetList(sort: Sort = defaultSort) {
    if (sort != null) {
      CommonUtils.fillSortParams(searchFormParams, sort);
    }
    CommonUtils.fillPaginationParams(searchFormParams, pagination);

    pageLoading.value = true;
    try {
      const { data } = await getAssetListApi(toRaw(searchFormParams));
      dataList.value = data.rows;
      pagination.total = data.total;
    } catch (e) {
      console.error("获取资产列表失败:", e);
    } finally {
      pageLoading.value = false;
    }
  }

  async function handleDelete(row) {
    await deleteAssetApi([row.assetId]).then(() => {
      message(`您删除了资产名称为${row.assetName}的这条数据`, {
        type: "success"
      });
      // 刷新列表
      getAssetList();
    });
  }

  async function handleBulkDelete(tableRef) {
    if (multipleSelection.value.length === 0) {
      message("请选择需要删除的数据", { type: "warning" });
      return;
    }

    ElMessageBox.confirm(
      `确认要<strong>删除</strong>编号为<strong style='color:var(--el-color-primary)'>[ ${multipleSelection.value} ]</strong>的资产吗?`,
      "系统提示",
      {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        dangerouslyUseHTMLString: true,
        draggable: true
      }
    )
      .then(async () => {
        await deleteAssetApi(multipleSelection.value).then(() => {
          message(`您删除了资产编号为[ ${multipleSelection.value} ]的数据`, {
            type: "success"
          });
          // 刷新列表
          getAssetList();
        });
      })
      .catch(() => {
        message("取消删除", {
          type: "info"
        });
        // 清空checkbox选择的数据
        tableRef.getTableRef().clearSelection();
      });
  }

  async function handleAdd(row, done) {
    await addAssetApi(row as AssetRequest).then(() => {
      message(`您新增了资产名称为${row.assetName}的这条数据`, {
        type: "success"
      });
      // 关闭弹框
      done();
      // 刷新列表
      getAssetList();
    });
  }

  async function handleUpdate(row, done) {
    await updateAssetApi(row as AssetRequest).then(() => {
      message(`您修改了资产名称为${row.assetName}的这条数据`, {
        type: "success"
      });
      // 关闭弹框
      done();
      // 刷新列表
      getAssetList();
    });
  }

  function openDialog(title = "新增", row?: AddAssetRequest) {
    addDialog({
      title: `${title}资产`,
      props: {
        formInline: {
          assetName: row?.assetName ?? "",
          assetCode: row?.assetCode ?? "",
          assetType: row?.assetType ?? "",
          status: row?.status ?? "",
          owner: row?.owner ?? "",
          purchaseDate: row?.purchaseDate ?? "",
          price: row?.price ?? undefined,
          remark: row?.remark ?? ""
        }
      },
      width: "40%",
      draggable: true,
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(editForm, { ref: formRef }),
      beforeSure: (done, { options }) => {
        const formRuleRef = formRef.value.getFormRuleRef();

        const curData = options.props.formInline as AddAssetRequest;

        formRuleRef.validate(valid => {
          if (valid) {
            // 表单规则校验通过
            if (title === "新增") {
              handleAdd(curData, done);
            } else {
              curData.assetId = row.assetId;
              handleUpdate(curData, done);
            }
          }
        });
      }
    });
  }

  onMounted(() => {
    getAssetList();
  });

  return {
    searchFormParams,
    pageLoading,
    columns,
    dataList,
    pagination,
    defaultSort,
    multipleSelection,
    getAssetList,
    onSearch,
    resetForm,
    openDialog,
    handleDelete,
    handleBulkDelete
  };
}