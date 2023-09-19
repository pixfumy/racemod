package net.pixfumy.racemod.mixin.worldgen;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.SpringFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(BiomeDecorator.class)
public class RemoveFlowingLavaBelowY60Mixin {
    @Redirect(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/SpringFeature;method_4028(Lnet/minecraft/world/World;Ljava/util/Random;III)Z"))
    private boolean stopLava(SpringFeature instance, World world, Random random, int i, int j, int k) {
        if (j < 60) {
            return false;
        }
        return instance.method_4028(world, random, i, j, k);
    }
}
