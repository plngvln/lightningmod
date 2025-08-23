package net.p4pingvin4ik.lightningmod;

import net.fabricmc.api.ModInitializer;
import net.p4pingvin4ik.lightningmod.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LightningMod implements ModInitializer {
    public static final String MOD_ID = "lightningmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Lightning Mod...");
        ModConfig.register();
        LOGGER.info("Lightning Mod initialized.");
    }
}