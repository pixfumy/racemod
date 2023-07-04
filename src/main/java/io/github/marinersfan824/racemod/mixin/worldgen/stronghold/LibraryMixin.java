package io.github.marinersfan824.racemod.mixin.worldgen.stronghold;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public class LibraryMixin {
    @Redirect(method = "method_979", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V",
    ordinal = 0))
    private void init(InGameHud instance, TextRenderer textRenderer, String s, int i, int j, int k) {
        instance.drawWithShadow(textRenderer, "_" + s, i, j, k);
    }
}
