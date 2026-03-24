package net.p4pingvin4ik.lightningmod.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.p4pingvin4ik.lightningmod.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeMixin {

    @Inject(method = "hasPrecipitation", at = @At("HEAD"), cancellable = true)
    private void forcePrecipitation(CallbackInfoReturnable<Boolean> cir) {
        if (ModConfig.get().lightningInAllBiomes) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getPrecipitationAt", at = @At("RETURN"), cancellable = true)
    private void makeRainVisibleInAllBiomes(BlockPos pos, int seaLevel, CallbackInfoReturnable<Biome.Precipitation> cir) {
        if (ModConfig.get().lightningInAllBiomes) {
            if (cir.getReturnValue() == Biome.Precipitation.NONE) {
                cir.setReturnValue(Biome.Precipitation.RAIN);
            }
        }
    }
}