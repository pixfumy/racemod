package net.pixfumy.racemod.mixin.dragon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragonEntity.class)
public abstract class StandardizeDragonRNGMixin extends Entity {
    public StandardizeDragonRNGMixin(World world) {
        super(world);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void foo(CallbackInfo ci) {
        if (!world.isClient) {
            this.random.setSeed(((ServerWorld)world).getServer().getWorld().getSeed() ^ 4491673419436809842L);
        }
    }
}
