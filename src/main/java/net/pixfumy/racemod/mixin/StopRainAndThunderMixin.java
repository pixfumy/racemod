package net.pixfumy.racemod.mixin;

import net.minecraft.world.World;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(World.class)
public class StopRainAndThunderMixin {
    @Redirect(method = "tickWeather", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelProperties;setRaining(Z)V"))
    private void stopRain(LevelProperties instance, boolean b) {

    }

    @Redirect(method = "tickWeather", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelProperties;setThundering(Z)V"))
    private void stopThunder(LevelProperties instance, boolean b) {

    }
}
