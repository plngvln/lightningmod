package net.p4pingvin4ik.lightningmod.mixins;

import net.minecraft.server.level.ServerLevel;
import net.p4pingvin4ik.lightningmod.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerLevel.class)
public class ServerWorldMixin {
    @ModifyConstant(
            method = "tickThunder(Lnet/minecraft/world/level/chunk/LevelChunk;)V",
            constant = @Constant(intValue = 100000)
    )
    private int modifyLightningChance(int original) {
        return ModConfig.get().lightningChance;
    }
    @ModifyConstant(
            method = "tickThunder(Lnet/minecraft/world/level/chunk/LevelChunk;)V",
            constant = @Constant(doubleValue = 0.01)
    )
    private double modifySkeletonHorseChance(double original) {
        return original * ModConfig.get().skeletonHorseChanceMultiplier;
    }
}