package dev.xkmc.literal_translation.mixin;

import dev.xkmc.literal_translation.handler.TranslationHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MutableComponent.class)
public class ComponentMixin {

	@ModifyArg(method = "create", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;<init>(Lnet/minecraft/network/chat/ComponentContents;Ljava/util/List;Lnet/minecraft/network/chat/Style;)V"))
	private static ComponentContents literal_translation$replaceLiteral(ComponentContents contents) {
		if (contents instanceof LiteralContents literal) {
			var trace = Thread.currentThread().getStackTrace();
			String clsId = trace[4].getClassName();
			if (clsId.equals(Component.class.getName()))
				clsId = trace[5].getClassName();
			var ans = TranslationHandler.getTranslation(clsId, literal.text());
			if (ans != null)
				return ans;
		}
		return contents;
	}

}
