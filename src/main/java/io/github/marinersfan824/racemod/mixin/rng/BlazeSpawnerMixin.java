package io.github.marinersfan824.racemod.mixin.rng;

import io.github.marinersfan824.racemod.RNGStreamGenerator;
import io.github.marinersfan824.racemod.mixinterface.ILevelProperties;
import net.minecraft.block.entity.SpawnerBlockEntityBehavior;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(SpawnerBlockEntityBehavior.class)
public abstract class BlazeSpawnerMixin {
    @Shadow private String entityId;

    @Shadow public abstract World getWorld();

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I"))
    private int spawnInStandardizedPos(Random instance, int bound) {
        if (!this.entityId.equals("Blaze") || this.getWorld().isClient) {
            return instance.nextInt(bound);
        }
        World overWorld = ((ServerWorld)this.getWorld()).getServer().getWorld();
        RNGStreamGenerator rngStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRngStreamGenerator();
        return new Random(rngStreamGenerator.getAndUpdateSeed("blazeSpawnSeed")).nextInt(bound);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextDouble()D"))
    private double spawnInStandardizedPos2(Random instance) {
        if (!this.entityId.equals("Blaze") || this.getWorld().isClient) {
            return instance.nextDouble();
        }
        World overWorld = ((ServerWorld)this.getWorld()).getServer().getWorld();
        RNGStreamGenerator rngStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRngStreamGenerator();
        return new Random(rngStreamGenerator.getAndUpdateSeed("blazeSpawnSeed")).nextDouble();
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F"))
    private float spawnInStandardizedPos3(Random instance) {
        if (!this.entityId.equals("Blaze") || this.getWorld().isClient) {
            return instance.nextFloat();
        }
        World overWorld = ((ServerWorld)this.getWorld()).getServer().getWorld();
        RNGStreamGenerator rngStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRngStreamGenerator();
        return new Random(rngStreamGenerator.getAndUpdateSeed("blazeSpawnSeed")).nextFloat();
    }

    @Redirect(method = "updateSpawns", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I"))
    private int setSpawnDelay(Random instance, int bound) {
        if (!this.entityId.equals("Blaze") || this.getWorld().isClient) {
            return instance.nextInt(bound);
        }
        World overWorld = ((ServerWorld)this.getWorld()).getServer().getWorld();
        RNGStreamGenerator rngStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRngStreamGenerator();
        return new Random(rngStreamGenerator.getAndUpdateSeed("blazeSpawnSeed")).nextInt(bound);
    }
}
