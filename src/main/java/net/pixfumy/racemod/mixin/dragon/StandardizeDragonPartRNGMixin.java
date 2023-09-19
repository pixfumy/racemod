package net.pixfumy.racemod.mixin.dragon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragonPart.class)
public abstract class StandardizeDragonPartRNGMixin extends Entity {
    public StandardizeDragonPartRNGMixin(World world) {
        super(world);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void foo(CallbackInfo ci) {
        if (!world.isClient) {
            this.random.setSeed(((ServerWorld)world).getServer().getWorld().getSeed() ^ -8111852249319927691L);
        }
    }
}
