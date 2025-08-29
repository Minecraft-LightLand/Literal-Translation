package dev.xkmc.literal_translation.handler;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.xkmc.literal_translation.init.LiteralTranslation;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = LiteralTranslation.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientCommands {


	@SubscribeEvent
	public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
		event.getDispatcher().register(literal("literaltranslation")
				.then(literal("clear")
						.executes(ctx -> {
							TranslationRegistry.clear();
							ctx.getSource().sendSystemMessage(Component.literal("Translation Cleared"));
							return 0;
						}))
				.then(literal("generate")
						.executes(ctx -> {
							if (TranslationRegistry.generate()) {
								ctx.getSource().sendSystemMessage(Component.literal("Translation Generated"));
								return 0;
							} else {
								ctx.getSource().sendSystemMessage(Component.literal("Translation Generation Failed"));
								return 1;
							}
						})));
	}

	public static LiteralArgumentBuilder<CommandSourceStack> literal(String name) {
		return LiteralArgumentBuilder.literal(name);
	}

}
