package net.p4pingvin4ik.lightningmod.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {

    public int lightningChance = 10000;
    public double skeletonHorseChanceMultiplier = 1.0;
    public boolean lightningInAllBiomes = false;

    private static ModConfig INSTANCE = null;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("lightningmod.json");


    public static void register() {
        INSTANCE = load();
        save();
    }

    public static ModConfig get() {
        if (INSTANCE == null) {
            INSTANCE = load();
        }
        return INSTANCE;
    }

    public static Screen getConfigScreen(Screen parent) {
        return ModConfigScreen.create(parent);
    }

    public static void save() {
        ModConfig current = get();
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(current, writer);
            }
        } catch (IOException ignored) {
        }
    }

    private static ModConfig load() {
        if (!Files.exists(CONFIG_PATH)) {
            return new ModConfig();
        }

        try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
            ModConfig parsed = GSON.fromJson(reader, ModConfig.class);
            return parsed != null ? parsed : new ModConfig();
        } catch (IOException | JsonParseException ignored) {
            return new ModConfig();
        }
    }
}