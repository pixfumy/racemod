package net.pixfumy.racemod.mixin.worldgen;

import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Biome.class)
public abstract class RemoveDesertHillsMixin {

    @Mutable
    @Shadow @Final public static Biome DESERT_HILLS;
    @Shadow @Final public static Biome DESERT;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void removeDesertHills(int id, CallbackInfo ci) {
        DESERT_HILLS = DESERT;
    }
}
