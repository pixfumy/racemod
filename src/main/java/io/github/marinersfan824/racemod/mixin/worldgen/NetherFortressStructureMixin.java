package io.github.marinersfan824.racemod.mixin.worldgen;

import net.minecraft.structure.NetherFortressStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(NetherFortressStructure.class)
public class NetherFortressStructureMixin {

    @Redirect(method = "shouldStartAt", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 0))
    private int moreFortresses(Random instance, int bound) {
        return instance.nextInt(bound) / 4;
    }

}
