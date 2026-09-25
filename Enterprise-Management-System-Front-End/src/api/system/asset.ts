import { http } from "@/utils/http";

/**
 * 资产配置查询条件
 */
export interface AssetQuery extends BasePageQuery {
  /** 资产名称 */
  assetName?: string;
  /** 资产编码 */
  assetCode?: string;
  /** 资产类型 */
  assetType?: string;
  /** 状态 */
  status?: string;
  /** 负责人 */
  owner?: string;
}

/**
 * AssetDTO, 资产信息
 */
export interface AssetDTO {
  assetId?: number;
  /** 资产名称 */
  assetName?: string;
  /** 资产编码 */
  assetCode?: string;
  /** 资产类型 */
  assetType?: string;
  /** 状态 */
  status?: string;
  /** 负责人 */
  owner?: string;
  /** 购入日期 */
  purchaseDate?: string;
  /** 价格 */
  price?: number;
  /** 备注 */
  remark?: string;
  /** 创建人名称 */
  creatorName?: string;
  /** 创建时间 */
  createTime?: string;
}

/**
 * AssetRequest, 新增/修改资产请求
 */
export interface AssetRequest {
  assetId?: number;
  /** 资产名称 */
  assetName: string;
  /** 资产编码 */
  assetCode: string;
  /** 资产类型 */
  assetType: string;
  /** 状态 */
  status: string;
  /** 负责人 */
  owner?: string;
  /** 购入日期 */
  purchaseDate?: string;
  /** 价格 */
  price?: number;
  /** 备注 */
  remark?: string;
}

/** 获取资产列表（分页） */
export const getAssetListApi = (params?: AssetQuery) => {
  return http.request<ResponseData<PageDTO<AssetDTO>>>("get", "/system/assets", {
    params
  });
};

/** 新增资产 */
export const addAssetApi = (data: AssetRequest) => {
  return http.request<ResponseData<void>>("post", "/system/assets", { data });
};

/** 修改资产 */
export const updateAssetApi = (data: AssetRequest) => {
  return http.request<ResponseData<void>>(
    "put",
    `/system/assets/${data.assetId}`,
    { data }
  );
};

/** 批量删除资产 */
export const deleteAssetApi = (data: Array<number>) => {
  return http.request<ResponseData<void>>("delete", "/system/assets", {
    params: {
      // 需要将数组转换为字符串  否则Axios会将参数变成 assetIds[0]:1  assetIds[1]:2 这种格式，后端接收参数不成功
      assetIds: data.toString()
    }
  });
};
