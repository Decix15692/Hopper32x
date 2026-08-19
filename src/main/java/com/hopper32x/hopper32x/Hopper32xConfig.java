package com.hopper32x.hopper32x;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Hopper32x 配置定义。
 *
 * <p>使用 NeoForge ModConfigSpec 生成 TOML 配置文件。</p>
 *
 * <p>配置文件生成与 {@code config/hopper32x-common.toml}中。</p>
 */
public final class Hopper32xConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    /**
     * 每 tick 传输物品数量。
     *
     * <p>原始为 1，默认 32，范围 1~1024。
     * 实际传输次数 = 此值（包含原始 1 次 + Mixin 注入的 N-1 次）。</p>
     */
    public static final ModConfigSpec.IntValue TRANSFER_PER_TICK = BUILDER
        .comment(
            "====================================",
            " Hopper32x Configuration",
            "====================================",
            "",
            "Items transferred per tick by hopper minecart.",
            "Default: 32 | Range: 1-1024",
            "Set to 1 to disable the mod effect (vanilla behavior)."
        ).defineInRange("transfer_per_tick", 32, 1, 1024);

    public static final ModConfigSpec SPEC = BUILDER.build();

    private Hopper32xConfig() {}
}
