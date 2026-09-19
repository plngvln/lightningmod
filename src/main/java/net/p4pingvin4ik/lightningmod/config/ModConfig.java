package net.p4pingvin4ik.lightningmod.config;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class ModConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue LIGHTNING_CHANCE = BUILDER
            .comment("The chance denominator for a lightning strike during a thunderstorm. Lower values mean more lightning.")
            .translation("config.lightningmod.lightning_chance")
            .defineInRange("lightningChance", 100000, 1, 100000);
    public static final ModConfigSpec.DoubleValue SKELETON_HORSE_CHANCE_MULTIPLIER = BUILDER
            .comment("Multiplier for the chance of spawning a skeleton horse trap after lightning.")
            .translation("config.lightningmod.skeleton_horse_chance_multiplier")
            .defineInRange("skeletonHorseChanceMultiplier", 1.0D, 0.0D, 1.0D);
    public static final ModConfigSpec.BooleanValue LIGHTNING_IN_ALL_BIOMES = BUILDER
            .comment("Allow lightning to strike in biomes where it normally cannot rain.")
            .translation("config.lightningmod.lightning_in_all_biomes")
            .define("lightningInAllBiomes", false);

    public static final ModConfigSpec SPEC = BUILDER.build();

    private ModConfig() {
    }

    public static void register(ModContainer modContainer) {
        modContainer.registerConfig(Type.SERVER, SPEC, "lightningmod-server.toml");
    }
}
