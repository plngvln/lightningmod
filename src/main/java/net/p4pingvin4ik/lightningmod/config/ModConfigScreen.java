package net.p4pingvin4ik.lightningmod.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.LongSliderControllerBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ModConfigScreen {

    public static Screen create(Screen parent) {
        final ModConfig config = ModConfig.get();

        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("title.lightningmod.config"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("category.lightningmod.main"))

                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("option.lightningmod.frequency"))
                                .description(opt -> {
                                    Integer val = opt.intValue();

                                    Component baseDesc = Component.translatable("tooltip.lightningmod.frequency");

                                    if (val != null && val < 5000) {
                                        return OptionDescription.of(
                                                baseDesc.copy()
                                                        .append(Component.literal("\n\n"))
                                                        .append(Component.translatable("tooltip.lightningmod.frequency.warning")
                                                                .withStyle(ChatFormatting.RED, ChatFormatting.BOLD))
                                        );
                                    }

                                    return OptionDescription.of(baseDesc);
                                })
                                .binding(
                                        100000,
                                        () -> config.lightningChance,
                                        v -> config.lightningChance = v
                                )
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                        .range(1, 100000)
                                        .step(1)
                                        .formatValue(v -> {
                                            if (v < 5000) {
                                                return Component.literal(v + " ")
                                                        .append(Component.translatable("gui.lightningmod.danger"))
                                                        .withStyle(ChatFormatting.RED);
                                            }
                                            return Component.literal(String.valueOf(v));
                                        })
                                )
                                .build())

                        .option(Option.<Long>createBuilder()
                                .name(Component.translatable("option.lightningmod.skeleton_horse_chance"))
                                .description(opt -> OptionDescription.of(
                                        Component.translatable("tooltip.lightningmod.skeleton_horse_chance")
                                ))
                                .binding(
                                        100L,
                                        () -> (long) Math.round(config.skeletonHorseChanceMultiplier * 100.0),
                                        v -> config.skeletonHorseChanceMultiplier = v / 100.0
                                )
                                .controller(opt -> LongSliderControllerBuilder.create(opt)
                                        .range(0L, 100L)
                                        .step(1L)
                                        .formatValue(v -> Component.literal(v + " %"))
                                )
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("option.lightningmod.all_biomes"))
                                .description(opt -> OptionDescription.of(
                                        Component.translatable("tooltip.lightningmod.all_biomes")
                                ))
                                .binding(
                                        false,
                                        () -> config.lightningInAllBiomes,
                                        v -> config.lightningInAllBiomes = v
                                )
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .build())
                .save(ModConfig::save)
                .build()
                .generateScreen(parent);
    }
}