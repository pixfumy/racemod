package io.github.marinersfan824.racemod.mixin.rng.mobspawn;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HostileEntity.class)
public class DisableTempleMobSpawnMixin {
    // TODO: Do this properly
    @Inject(method = "method_3087", at = @At("HEAD"), cancellable = true)
    private void checkIfInTemple(CallbackInfoReturnable<Boolean> cir) {
        Entity thisEntity = (Entity) (Object) this;
        int x = (int) thisEntity.x;
        int y = (int) thisEntity.y;
        int z = (int) thisEntity.z;
        Block standingBlock = thisEntity.world.method_3774(x, y-1, z);
        long timeOfDay = thisEntity.world.getTimeOfDay() % 24000;
        if ((standingBlock == Blocks.SANDSTONE || standingBlock == Blocks.WOOL) && timeOfDay < 13000) {
            cir.setReturnValue(false);
        }
    }
}
