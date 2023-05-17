package io.github.marinersfan824.racemod.mixin.rng;

import io.github.marinersfan824.racemod.RNGStreamGenerator;
import io.github.marinersfan824.racemod.mixinterface.ILevelProperties;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(SkeletonEntity.class)
public abstract class SkeletonEntityMixin extends LivingEntity {
    /**
     * All classes that extend Entity <b>must<b> have a constructor
     * that takes in one, and only one {@link World} parameter.
     * This is due to the fact that entity constructors are called reflectively
     * from {@link net.minecraft.entity.EntityDispatcher}
     *
     * @param world
     */
    public SkeletonEntityMixin(World world) {
        super(world);
    }

    @Redirect(method = "dropLoot", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 1))
    private int dropStandardizedLoot(Random instance, int bound) {
        World overWorld = ((ServerWorld)this.world).getServer().getWorld();
        RNGStreamGenerator rngStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRngStreamGenerator();
        return new Random(rngStreamGenerator.getAndUpdateSeed("arrowSeed")).nextInt(bound);
    }
}
