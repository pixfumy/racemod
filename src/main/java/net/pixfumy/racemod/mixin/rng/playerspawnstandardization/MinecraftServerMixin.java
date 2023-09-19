package net.pixfumy.racemod.mixin.rng.playerspawnstandardization;

import net.pixfumy.racemod.Main;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "prepareWorlds", at = @At("HEAD"))
    private void shouldSetPlayerSpawn(CallbackInfo ci) {
        Main.shouldSetPlayerSpawn = true;
    }
}
