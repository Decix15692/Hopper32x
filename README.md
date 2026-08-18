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
- 范围：1（原版行为）到 1024
- 热重载：修改配置文件后游戏内即时生效，无需重启
- 极小侵入：仅一处 Mixin 注入，不添加任何游戏内容

## 安装

1. 下载模组 JAR 文件，放入 `.minecraft/version/your_minecraft_version/mods/` 目录
2. 确认 NeoForge 版本 = 21.11.44
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

## 工作原理

模组通过 Mixin 注入 `MinecartHopper.tick()` 方法，注入点为 `@At("RETURN")`。原版 tick 逻辑执行完毕后（其中会调用一次 `suckInItems()`），Mixin 额外调用 `suckInItems()` N-1 次，N 为配置的 `transfer_per_tick` 值。

```
原版 tick()  ->  suckInItems() x1
Hopper32x tick() -> suckInItems() x N  （1 次原版 + N-1 次注入）
```

注入点选择 public 方法 `tick()` 的 `@At("RETURN")`，而非通过 `@At(INVOKE)` 匹配内部 private 方法。这样避免了因字节码内联或映射差异导致的指令匹配失败问题。

## 项目结构

```
hopper32x/
  src/main/java/com/hopper32x/hopper32x/
    Hopper32x.java              -- 模组入口，注册配置
    Hopper32xConfig.java        -- ModConfigSpec 配置定义
    mixin/
      MinecartHopperMixin.java  -- Mixin：注入额外 suckInItems() 调用
  src/main/templates/META-INF/
    neoforge.mods.toml          -- 模组元数据（含 Mixin 声明）
  src/main/resources/
    hopper32x.mixins.json       -- Mixin 配置文件
  build.gradle                  -- ModDevGradle 构建脚本
  gradle.properties             -- 版本与项目属性
```

## 构建

```bash
./gradlew build
```

输出的 JAR 文件位于 `build/libs/`。

## 技术说明

- 配置类型为 `COMMON`，存储在服务端并同步至客户端，单人和多人游戏均可生效
- `ModConfigSpec.IntValue.get()` 使用内部缓存，每 tick 调用无性能问题
- Mixin 通过 `@Shadow` 访问 `suckInItems()` 和 `isEnabled()`，避免反射开销
- 模组仅在漏斗矿车处于启用状态时生效（未被红石禁用且不在冷却中）

## 许可证

MIT

