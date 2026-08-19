package com.hopper32x.hopper32x;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import com.hopper32x.hopper32x.Hopper32xConfig;

/**
 * Hopper Minecart 32x — 模组入口。
 */
@Mod(Hopper32x.MOD_ID)
public final class Hopper32x {
    public static final String MOD_ID = "hopper32x";

    public Hopper32x(IEventBus modEventBus, ModContainer modContainer) {
         modContainer.registerConfig(ModConfig.Type.COMMON, Hopper32xConfig.SPEC);
    }

    private static String minecraftVersion() {
        String v = System.getProperty("minecraft.version");
        return v != null ? v : "1.21.11 (build-time)";
    }

    private static String neoVersion() {
        String v = System.getProperty("neo.version");
        return v != null ? v : "21.11.44 (build-time)";
    }
}
