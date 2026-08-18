# Hopper Minecart 32x 文档

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

##生成JAR文件

```bash
./gradlew jar
```

输出的 JAR 文件位于 `build/libs/`目录下。

## 技术说明

- 配置类型为 `COMMON`，存储在服务端并同步至客户端，单人和多人游戏均可生效
- `ModConfigSpec.IntValue.get()` 使用内部缓存，每 tick 调用无性能问题
- Mixin 通过 `@Shadow` 访问 `suckInItems()` 和 `isEnabled()`，避免反射开销
- 模组仅在漏斗矿车处于启用状态时生效（未被红石禁用且不在冷却中）
