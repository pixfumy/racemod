package io.github.marinersfan824.racemod.mixin.rng;

import io.github.marinersfan824.racemod.RNGStreamGenerator;
import io.github.marinersfan824.racemod.mixinterface.ILevelProperties;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PigEntity.class)
public abstract class PigEntityMixin extends LivingEntity {
    @Shadow public abstract boolean isSaddled();

    private RNGStreamGenerator rngStreamGenerator;

    public PigEntityMixin(World world) {
        super(world);
    }

    @Inject(method = "dropLoot", at = @At("HEAD"), cancellable = true)
    private void dropStandardizedLoot(boolean allowDrops, int lootingMultiplier, CallbackInfo ci) {
        World overWorld = ((ServerWorld)this.world).getServer().getWorld();
        rngStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRngStreamGenerator();
        long seedResult = rngStreamGenerator.getAndUpdateSeed("porkChopSeed");
        int numDrops = 1 + (int) (seedResult % (3 + lootingMultiplier));
        for (int j = 0; j < numDrops; ++j) {
            if (this.isOnFire()) {
                this.dropItem(Items.COOKED_PORKCHOP, 1);
            } else {
                this.dropItem(Items.RAW_PORKCHOP, 1);
            }
        }
        if (this.isSaddled()) {
            this.dropItem(Items.SADDLE, 1);
        }
        ci.cancel();
    }
}
