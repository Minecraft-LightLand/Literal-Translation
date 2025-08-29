package dev.xkmc.literal_translation.init;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LiteralTranslation.MODID)
public class LiteralTranslation {

	public static final String MODID = "literal_translation";
	public static final Logger LOGGER = LogManager.getLogger();

	public LiteralTranslation() {
		LTConfig.init();
	}

	@SubscribeEvent
	public static void registerCaps(FMLCommonSetupEvent event) {
	}

}
