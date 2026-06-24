package net.p4pingvin4ik.lightningmod.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.p4pingvin4ik.lightningmod.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    @ModifyConstant(
            method = "tickThunder",
            constant = @Constant(intValue = 100000)
    )
    private int modifyLightningChance(int original) {
        int chance = ModConfig.get().lightningChance;
        return chance > 0 ? chance : original;
    }

    @ModifyConstant(
            method = "tickThunder",
            constant = @Constant(doubleValue = 0.01)
    )
    private double modifySkeletonHorseChance(double original) {
        return original * ModConfig.get().skeletonHorseChanceMultiplier;
    }

    @Redirect(
            method = "tickThunder",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;isRainingAt(Lnet/minecraft/core/BlockPos;)Z"
            )
    )
    private boolean forceLightningInAllBiomes(ServerLevel instance, BlockPos pos) {
        if (ModConfig.get().lightningInAllBiomes) {
            return true;
        }

        return instance.isRainingAt(pos);
    }
}