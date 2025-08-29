package dev.xkmc.literal_translation.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.xkmc.literal_translation.init.LTConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;

public class TranslationRegistry {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	private static final TreeMap<String, String> SHORT = new TreeMap<>();
	private static final TreeMap<String, String> LONG = new TreeMap<>();

	public static boolean hasAbc(String key) {
		for (int i = 0; i < key.length(); i++) {
			char ch = key.charAt(i);
			if ('a' <= ch && ch <= 'z' || 'A' <= ch && ch <= 'Z')
				return true;
		}
		return false;
	}

	public static void register(String cls, String key) {
		if (!LTConfig.CLIENT_SPEC.isLoaded()) return;
		if (!LTConfig.CLIENT.enableDetection.get()) return;
		boolean noAbc = LTConfig.CLIENT.generateNoAlphabetStrings.get();
		if (!noAbc && !hasAbc(key)) return;
		SHORT.put(key, key);
		LONG.put(cls + "." + key, key);
	}

	public static void clear() {
		SHORT.clear();
		LONG.clear();
	}

	public static boolean generate() {
		TreeMap<String, String> map = LTConfig.CLIENT.generateClassSpecificTranslations.get() ? LONG : SHORT;
		boolean noAbc = LTConfig.CLIENT.generateNoAlphabetStrings.get();
		JsonObject obj = new JsonObject();
		for (var ent : map.entrySet()) {
			if (!noAbc && !hasAbc(ent.getValue())) continue;
			obj.addProperty("literal_translation." + ent.getKey(), ent.getValue());
		}
		if (!writeLang(obj)) return false;
		clear();
		return true;
	}

	public static boolean writeLang(JsonObject object) {
		var path = FMLPaths.GAMEDIR.get().resolve("resourcepacks/LiteralTranslationGeneratedLanguageFiles");
		var folder = path.toFile();
		if (!folder.exists()) {
			if (!folder.mkdirs()) return false;
		} else if (!folder.isDirectory()) return false;
		try {
			var meta = path.resolve("pack.mcmeta").toFile();
			if (meta.exists()) meta.delete();
			if (!meta.createNewFile()) return false;
			try (FileWriter w = new FileWriter(meta, StandardCharsets.UTF_8)) {
				w.write(GSON.toJson(getMeta()));
			}
		} catch (Exception ignored) {
			return false;
		}
		try {
			var inner = path.resolve("assets/literal_translation/lang");
			folder = inner.toFile();
			if (!folder.exists()) {
				if (!folder.mkdirs()) return false;
			} else if (!folder.isDirectory()) return false;
			var lang = inner.resolve("en_us.json").toFile();
			if (lang.exists()) {
				JsonObject old = new JsonParser().parse(new FileReader(lang.getPath())).getAsJsonObject();
				for (var ent : old.entrySet()) {
					if (!object.has(ent.getKey())) {
						object.add(ent.getKey(), ent.getValue());
					}
				}
				lang.delete();
			}
			if (!lang.createNewFile()) return false;
			try (FileWriter w = new FileWriter(lang, StandardCharsets.UTF_8)) {
				w.write(GSON.toJson(object));
			}
			return true;
		} catch (Exception ignored) {
			return false;
		}
	}

	private static JsonObject getMeta() {
		var pack = new JsonObject();
		pack.addProperty("description", "Literal Translation Generated Resource Pack");
		pack.addProperty("pack_format", 15);
		var ans = new JsonObject();
		ans.add("pack", pack);
		return ans;
	}

}
