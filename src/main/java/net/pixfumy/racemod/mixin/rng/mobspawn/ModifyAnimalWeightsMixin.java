package net.pixfumy.racemod.mixin.rng.mobspawn;

import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Biome.class)
public abstract class ModifyAnimalWeightsMixin {
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/SpawnEntry;<init>(Ljava/lang/Class;III)V", ordinal = 0), index = 1)
    private int decreaseSheepSpawning(int weight) {
        return 2;
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/SpawnEntry;<init>(Ljava/lang/Class;III)V", ordinal = 2), index = 1)
    private int increaseChickenSpawning(int weight) {
        return 20;
    }
}
