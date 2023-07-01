package io.github.marinersfan824.racemod.mixin.worldgen;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.LakesFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LakesFeature.class)
public class RemoveLavaLakesBelowY55Mixin {
    @Inject(method = "method_4028", at = @At("HEAD"), cancellable = true)
    public void method_4028(World world, Random random, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
        if (j <= 55) {
            cir.setReturnValue(false);
        }
    }
}
