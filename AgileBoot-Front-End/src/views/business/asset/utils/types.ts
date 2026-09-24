interface AddAssetRequest {
  assetId?: number;
  /** 资产名称 */
  assetName: string;
  /** 资产编码 */
  assetCode: string;
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
}

interface FormProps {
  formInline: AddAssetRequest;
}

export type { AddAssetRequest, FormProps };