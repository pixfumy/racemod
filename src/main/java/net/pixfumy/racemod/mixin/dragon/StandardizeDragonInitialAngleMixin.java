package net.pixfumy.racemod.mixin.dragon;

import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.EndBiomeDecorator;
import net.minecraft.world.chunk.EndChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(EndBiomeDecorator.class)
public class StandardizeDragonInitialAngleMixin extends BiomeDecorator {
    @Redirect(method = "generate", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F"))
    private float standardizeDragonInitialAngle(Random instance) {
        return new Random(world.getSeed() ^ -2595072006442097346L).nextFloat();
    }
}
