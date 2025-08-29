package dev.xkmc.literal_translation.init;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class LTConfig {

	public static class Client {

		public final ForgeConfigSpec.BooleanValue generateClassSpecificTranslations;
		public final ForgeConfigSpec.BooleanValue generateNoAlphabetStrings;
		public final ForgeConfigSpec.BooleanValue enableDetection;

		Client(ForgeConfigSpec.Builder builder) {
			generateClassSpecificTranslations = builder.comment("Generate different translation keys for the same content if they are invoked by different classes")
					.define("generateClassSpecificTranslations", false);
			generateNoAlphabetStrings = builder.comment("Generate translations for strings with no alphabets")
					.define("generateNoAlphabetStrings", false);
			enableDetection = builder.comment("Enable Literal Detection. Disable it after generating all translations to improve performance.")
					.define("enableDetection", true);

		}

	}

	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	static {

		final Pair<Client, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = common.getRight();
		CLIENT = common.getLeft();
	}

	/**
	 * Registers any relevant listeners for config
	 */
	public static void init() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC);
	}


}
