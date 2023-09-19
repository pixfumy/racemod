package net.pixfumy.racemod.mixin.worldgen;

import net.minecraft.world.chunk.SurfaceChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(SurfaceChunkGenerator.class)
public class IncreaseLavaPoolsMixin {

    @Redirect(method = "decorateChunk", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 4))
    private int moreLavapools(Random random, int bound) {
        return random.nextInt(bound * 2) / 3;
    }
}