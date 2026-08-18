# Hopper Minecart 32x

一个轻量级 NeoForge 模组，提升漏斗矿车的物品传输速率。原版漏斗矿车每 tick 传输 1 个物品，本模组将其提升至 32 个（可配置，最高 1024）。

## 环境要求

| 依赖 | 版本 |
|------|------|
| Minecraft | 1.21.11 |
| NeoForge | 21.11.44 |
| Java | 21 |
| ModDevGradle | 2.0.143 |

## 功能

- 漏斗矿车每 tick 传输 32 个物品（原版为 1）
- 传输速率通过配置文件调节
- 范围：1（原版行为）~ 1024
- 热重载：修改配置文件后游戏内即时生效，无需重启游戏
- 极小侵入：仅一处 Mixin 注入，不添加任何游戏内容

## 重要声明
某些功能类似的模组或对 tick 进行修改过的模组可能会出现兼容性错误
如果出现这种情况，请提交issue

## 安装

1. 下载模组 JAR 文件，放入 `.minecraft/version/your_minecraft_version/mods/` 目录
2. 确认 NeoForge 版本 ≥ 21.11.44
3. 启动 Minecraft

## 配置

首次启动后，模组会自动生成 `config/hopper32x-common.toml`：

```toml
#====================================
# Hopper32x Configuration
#====================================
#
#Items transferred per tick by hopper minecart.
#Default: 32 | Range: 1-1024
#Set to 1 to disable the mod effect (vanilla behavior).
transfer_per_tick = 32
```

| 参数 | 默认值 | 范围 | 说明 |
|------|--------|------|------|
| `transfer_per_tick` | 32 | 1 - 1024 | 每 tick 传输物品数。设为 1 则与原版行为一致。 |

保存配置文件后即时生效，无需重启游戏。

###其他技术性内容请到[这里](DOCUMENT.md)

## 许可证

MIT