package io.github.marinersfan824.racemod.mixin.rng;

import net.minecraft.world.biome.NetherBiome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(NetherBiome.class)
public class NetherBiomeMixin {

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0))
    private boolean disableGhasts(List<?> instance, Object e) {
        return false;
    }
}