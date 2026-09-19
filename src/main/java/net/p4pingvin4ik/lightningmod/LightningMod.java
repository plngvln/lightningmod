package net.p4pingvin4ik.lightningmod;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.p4pingvin4ik.lightningmod.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(LightningMod.MOD_ID)
public class LightningMod {
    public static final String MOD_ID = "lightningmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public LightningMod(ModContainer modContainer) {
        LOGGER.info("Initializing Lightning Mod...");
        ModConfig.register(modContainer);
        LOGGER.info("Lightning Mod initialized.");
    }
}
