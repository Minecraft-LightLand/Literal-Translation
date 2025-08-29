package dev.xkmc.literal_translation.handler;

import net.minecraft.locale.Language;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.jetbrains.annotations.Nullable;

public class TranslationHandler {

	@Nullable
	public static ComponentContents getTranslation(String cls, String key) {
		if (Language.getInstance().has("literal_translation." + key)) {
			return new TranslatableContents("literal_translation." + key, (String) null, TranslatableContents.NO_ARGS);
		}
		if (Language.getInstance().has("literal_translation." + cls + "." + key)) {
			return new TranslatableContents("literal_translation." + cls + "." + key, (String) null, TranslatableContents.NO_ARGS);
		}
		TranslationRegistry.register(cls, key);
		return null;
	}

}
