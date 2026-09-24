# 企业管理系统 Monorepo

基于开源脚手架 [AgileBoot](https://github.com/valarchie/AgileBoot-Back-End)（Spring Boot + Vue3 前后端分离）二次开发，扩展了**资产管理**业务模块。

## 致谢

- [AgileBoot-Back-End](https://github.com/valarchie/AgileBoot-Back-End) — 后端脚手架（valarchie）
- [AgileBoot-Front-End](https://github.com/valarchie/AgileBoot-Front-End) — 前端脚手架（基于 Pure-Admin）

本仓库将前后端整合为 monorepo 管理，在此基础上新增了资产管理功能。

## 项目结构

```
agilboot/
├── AgileBoot-Front-End/              # 前端项目（Vue3 + Vite + Element Plus）
│   └── src/
│       ├── api/system/               # API 接口
│       ├── views/system/asset/       # 资产管理页面
│       └── ...
└── AgileBoot-Back-End-main/          # 后端项目（Spring Boot + MyBatis-Plus）
    └── AgileBoot-Back-End-main/
        ├── ems-admin/           # 启动模块 + Controller 层
        ├── ems-domain/          # 领域层（Entity/Model/Service/DTO/Query/Command）
        ├── ems-infrastructure/ # 基础设施（MyBatis/安全/缓存/日志）
        ├── ems-common/          # 公共组件（枚举/工具/异常）
        ├── ems-api/             # API 定义
        └── sql/                       # 数据库初始化脚本
```

## 技术栈

### 后端
- Java 8+ / Spring Boot 2.7.10
- MyBatis-Plus
- MySQL / PostgreSQL / H2（测试）
- Redis（生产环境）

### 前端
- Vue 3 + TypeScript
- Vite 4
- Element Plus
- Pinia + Vue Router

## 快速开始

### 后端

```bash
cd AgileBoot-Back-End-main/AgileBoot-Back-End-main
./mvnw.cmd spring-boot:run -pl ems-admin "-Dspring-boot.run.profiles=basic,dev"
# 默认端口 http://localhost:8080
```

开发环境需要 MySQL 和 Redis。想无依赖直接启动：

```bash
# application.yml 中设置
agileboot:
  embedded:
    mysql: true
    redis: true
# 然后
./mvnw.cmd spring-boot:run -pl ems-admin "-Dspring-boot.run.profiles=basic,test"
```

### 前端

```bash
cd AgileBoot-Front-End
pnpm install
pnpm dev
# 默认端口 http://localhost:3000
```

前端通过 Vite 代理 `/dev-api` → `http://localhost:8080`，开发时后端 API 直接透传。

### 数据库初始化

MySQL 执行 `sql/ems-20260924.sql`，完成建表和初始数据（菜单、权限、字典等）。

PostgreSQL 执行 `ems-infrastructure/src/main/resources/pgsql/` 下的两个文件。

---

## 本次改动：资产管理模块

### 后端（15 个新增文件 + 1 个修改）

| 层 | 文件 | 作用 |
|---|---|---|
| Controller | `ems-admin/.../SysAssetController.java` | REST API（GET 列表 / POST 新增 / PUT 修改 / DELETE 删除） |
| 枚举 | `ems-common/.../AssetTypeEnum.java` | 资产类型字典（电子设备 / 办公设备 / 耗材） |
| 枚举 | `ems-common/.../AssetStatusEnum.java` | 资产状态字典（在用 / 闲置 / 维修中 / 报废） |
| Service | `ems-domain/.../AssetApplicationService.java` | 业务编排（增删改查 + 名称/编码唯一性校验） |
| Command | `.../command/AddAssetCommand.java` / `UpdateAssetCommand.java` | 新增 / 修改入参 |
| DB | `.../db/SysAssetEntity.java` / `SysAssetMapper.java` / `SysAssetService.java` / `SysAssetServiceImpl.java` | MyBatis-Plus 数据库访问层 |
| DTO | `.../dto/AssetDTO.java` | 返回前端的数据结构 |
| Model | `.../model/AssetModel.java` / `AssetModelFactory.java` | 领域模型 |
| Query | `.../query/AssetQuery.java` | 分页查询条件 |
| 缓存 | `MapCache.java`（修改） | 注册 AssetTypeEnum / AssetStatusEnum 到字典缓存 |

### 前端（6 个新增文件）

| 文件 | 作用 |
|---|---|
| `src/api/system/asset.ts` | HTTP API（getAssetListApi / addAssetApi / updateAssetApi / deleteAssetApi） |
| `src/views/system/asset/index.vue` | 资产列表页（搜索栏 + 数据表格 + 添加/批量删除按钮 + 行内修改/删除） |
| `src/views/system/asset/form.vue` | 新增 / 编辑资产弹窗表单 |
| `src/views/system/asset/utils/hook.tsx` | 列表逻辑（搜索、分页、CRUD、字典渲染） |
| `src/views/system/asset/utils/rule.ts` | 表单校验规则 |
| `src/views/system/asset/utils/types.ts` | TypeScript 类型定义 |

### 数据库（3 套 SQL 均已更新）

- **sys_asset 表**：asset_id / asset_name / asset_code / asset_type / status / owner / purchase_date / price / remark / creator / create_time ...
- **菜单**（menu_id 66-70）：
  - `66` 资产管理（menu_type=1, path=/system/asset/index）
  - `67` 资产查询（按钮, permission=system:asset:query）
  - `68` 资产新增（按钮, permission=system:asset:add）
  - `69` 资产修改（按钮, permission=system:asset:edit）
  - `70` 资产删除（按钮, permission=system:asset:remove）
- **角色权限**：role_id=2 绑定 menu_id 66-70

### 字典（自动缓存）

- `sysAsset.assetType` → 电子设备(1) / 办公设备(2) / 耗材(3)
- `sysAsset.status` → 在用(1) / 闲置(2) / 维修中(3) / 报废(4)

前端下拉框和后端 `@PreAuthorize("@permission.has('system:asset:add')")` 均依赖以上配置。

---

## 许可

MIT License
