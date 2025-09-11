package net.p4pingvin4ik.lightningmod.mixins;

import net.p4pingvin4ik.lightningmod.config.ModConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeMixin {

    @Inject(method = "getPrecipitation(Lnet/minecraft/util/math/BlockPos;I)Lnet/minecraft/world/biome/Biome$Precipitation;", at = @At("RETURN"), cancellable = true)
    private void makeRainVisibleInAllBiomes(BlockPos pos, int seaLevel, CallbackInfoReturnable<Biome.Precipitation> cir) {
        if (ModConfig.get().lightningInAllBiomes) {
            if (cir.getReturnValue() == Biome.Precipitation.NONE) {
                cir.setReturnValue(Biome.Precipitation.RAIN);
            }
        }
    }
}