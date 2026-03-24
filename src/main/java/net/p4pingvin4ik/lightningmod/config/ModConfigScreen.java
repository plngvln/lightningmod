package net.p4pingvin4ik.lightningmod.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ModConfigScreen {

    public static Screen create(Screen parent) {
        final ModConfig config = ModConfig.get();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("title.lightningmod.config"));

        builder.setSavingRunnable(() -> {
            AutoConfig.getConfigHolder(ModConfig.class).save();
        });

        ConfigCategory main = builder.getOrCreateCategory(Component.translatable("category.lightningmod.main"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        main.addEntry(entryBuilder.startIntSlider(
                        Component.translatable("option.lightningmod.frequency"),
                        config.lightningChance,
                        1,
                        100000
                )
                .setDefaultValue(100000)
                .setTooltip(Component.translatable("tooltip.lightningmod.frequency"))
                .setSaveConsumer(newValue -> config.lightningChance = newValue)
                .build());


        main.addEntry(entryBuilder.startLongSlider(
                        Component.translatable("option.lightningmod.skeleton_horse_chance"),
                        (long) (config.skeletonHorseChanceMultiplier * 100L),
                        0L,
                        100L
                )
                .setDefaultValue(100L)
                .setTooltip(Component.translatable("tooltip.lightningmod.skeleton_horse_chance"))
                .setSaveConsumer(newValue -> config.skeletonHorseChanceMultiplier = newValue / 100.0)
                .setTextGetter(value -> Component.nullToEmpty(value + " %"))
                .build());
        main.addEntry(entryBuilder.startBooleanToggle(
                        Component.translatable("option.lightningmod.all_biomes"),
                        config.lightningInAllBiomes
                )
                .setDefaultValue(false)
                .setTooltip(Component.translatable("tooltip.lightningmod.all_biomes"))
                .setSaveConsumer(newValue -> config.lightningInAllBiomes = newValue)
                .build());

        return builder.build();
    }
}