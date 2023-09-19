package net.pixfumy.racemod;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    public static boolean shouldSetPlayerSpawn;

    @Override
	public void onInitialize() {
        LOGGER.info("Racemod loaded.");
	}
}
