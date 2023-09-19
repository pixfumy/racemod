package net.pixfumy.racemod.mixin.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.RavineCarver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RavineCarver.class)
public class RemoveRavineMixin extends Carver {
    @Redirect(method = "method_4003", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/carver/RavineCarver;method_3996(JII[Lnet/minecraft/block/Block;DDDFFFIID)V"))
    protected void ravineOverride(RavineCarver instance, long i, int j, int blocks, Block[] d, double e, double f, double g, float h, float k, float m, int n, int o, double v) {

    }
}
