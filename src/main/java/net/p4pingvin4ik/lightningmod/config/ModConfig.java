package net.p4pingvin4ik.lightningmod.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.client.gui.screens.Screen;

@Config(name = "lightningmod")
public class ModConfig implements ConfigData {

    public int lightningChance = 10000;
    public double skeletonHorseChanceMultiplier = 1.0;
    public boolean lightningInAllBiomes = false;

    private static ModConfig INSTANCE = null;


    public static void register() {
        ConfigHolder<ModConfig> holder = AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);

        INSTANCE = holder.getConfig();

        holder.registerSaveListener((h, config) -> {
            INSTANCE = config;
            return null;
        });
    }

    public static ModConfig get() {
        if (INSTANCE == null) {
            return new ModConfig();
        }
        return INSTANCE;
    }

    public static Screen getConfigScreen(Screen parent) {
        return ModConfigScreen.create(parent);
    }
}