package io.github.marinersfan824.racemod.mixin.worldgen;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.StoneFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(BiomeDecorator.class)
public class BiomeDecoratorMixin {
    @Redirect(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/StoneFeature;method_4028(Lnet/minecraft/world/World;Ljava/util/Random;III)Z"))
    private boolean stopLava(StoneFeature instance, World world, Random random, int i, int j, int k) {
        if (j < 60) {
            return false;
        }
        return instance.method_4028(world, random, i, j, k);
    }
}
