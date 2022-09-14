package io.github.marinersfan824.racemod.mixin.rng;

import io.github.marinersfan824.racemod.mixinterface.ILevelProperties;
import io.github.marinersfan824.racemod.RNGStreamGenerator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.thrown.EyeOfEnderEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EyeOfEnderEntity.class)
public abstract class EyeOfEnderMixin extends Entity {
    @Shadow private boolean dropsItem;
    private RNGStreamGenerator rngStreamGenerator;
    public EyeOfEnderMixin(World world) {
        super(world);
    }

    @Inject(method = "method_3228", at = @At("TAIL"))
    public void overrideEyeThrow(double i, int e, double par3, CallbackInfo ci) {
        World overWorld = ((ServerWorld)this.world).getServer().getWorld();
        this.rngStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRngStreamGenerator();
        long seedResult = rngStreamGenerator.getAndUpdateSeed("enderEyeSeed");
        this.dropsItem = seedResult % 5 > 0;
    }
}
