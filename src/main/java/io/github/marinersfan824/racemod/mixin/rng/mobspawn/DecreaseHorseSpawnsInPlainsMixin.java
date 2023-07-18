package io.github.marinersfan824.racemod.mixin.rng.mobspawn;

import net.minecraft.world.biome.PlainsBiome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PlainsBiome.class)
public class DecreaseHorseSpawnsInPlainsMixin {
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/SpawnEntry;<init>(Ljava/lang/Class;III)V", ordinal = 0), index = 1)
    private int decreaseHorseSpawning(int weight) {
        return 2;
    }
}
