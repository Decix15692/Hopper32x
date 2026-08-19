package com.hopper32x.hopper32x.mixin;

import com.hopper32x.hopper32x.Hopper32x;
import com.hopper32x.hopper32x.Hopper32xConfig;
import net.minecraft.world.entity.vehicle.minecart.MinecartHopper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for MinecartHopper — Phase 2 核心。
 *
 * <p>注入 tick()（public 方法），在 tick 结束后追加 31 次 suckInItems() 调用，
 * 实现每 tick 传输 32 物品（原始 1 + 额外 31）。</p>
 *
 * <p>原始 tick() 内部调用 tryConsumeItems() → suckInItems() 一次（如果启用），
 * 我们在 tick() 返回后再调 31 次，总计 32 次/tick。</p>
 *
 *<p>N 由配置 {@code transfer_per_tick} 控制（默认 32，范围 1-1024）。</p>
 
 * <p>用 @At("RETURN") 避免精确匹配 INVOKE 指令的脆弱性。
 * tick() 是 public 方法，不存在 private 方法注入问题。</p>
 *
 */
@Mixin(MinecartHopper.class)
public abstract class MinecartHopperMixin {
    @Shadow
    public abstract boolean suckInItems();

    @Shadow
    public abstract boolean isEnabled();

    /**
     * 在 tick() 返回后追加 N-1 次 suckInItems() 调用。
     *
     * <p>原始 tick() 已通过 tryConsumeItems() 调用 suckInItems() 1 次（如果启用）。
     * 此处再调 N-1 次，总计 N 次/tick。
     * 仅当漏斗矿车启用时执行，避免禁用状态下多余调用。</p>
     *
     * <p>N 从配置读取，配置未加载时返回默认值 32。</p>
     */
    @Inject(method = "tick()V", at = @At("HEAD"))
    private void hopper32x$suckMoreItems(CallbackInfo ci) {
        if (this.isEnabled()) {
            int transferPerTick = Hopper32xConfig.TRANSFER_PER_TICK.get();
            for (int i = 1; i < transferPerTick; i++) {
                this.suckInItems();
            }
        }
    }
}