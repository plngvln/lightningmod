package net.p4pingvin4ik.lightningmod.mixins;

import net.minecraft.server.level.ServerLevel;
import net.p4pingvin4ik.lightningmod.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

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
}