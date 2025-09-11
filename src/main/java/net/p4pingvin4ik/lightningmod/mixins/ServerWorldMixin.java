package net.p4pingvin4ik.lightningmod.mixins;

import net.p4pingvin4ik.lightningmod.config.ModConfig;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @ModifyConstant(
            method = "tickThunder(Lnet/minecraft/world/chunk/WorldChunk;)V",
            constant = @Constant(intValue = 100000)
    )
    private int modifyLightningChance(int original) {
        return ModConfig.get().lightningChance;
    }
    @ModifyConstant(
            method = "tickThunder(Lnet/minecraft/world/chunk/WorldChunk;)V",
            constant = @Constant(doubleValue = 0.01)
    )
    private double modifySkeletonHorseChance(double original) {
        return original * ModConfig.get().skeletonHorseChanceMultiplier;
    }
}