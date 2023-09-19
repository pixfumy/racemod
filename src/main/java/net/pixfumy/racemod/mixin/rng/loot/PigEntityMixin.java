package net.pixfumy.racemod.mixin.rng.loot;

import net.pixfumy.racemod.RNGStreamGenerator;
import net.pixfumy.racemod.mixinterface.ILevelProperties;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(PigEntity.class)
public abstract class PigEntityMixin extends LivingEntity {
    @Shadow public abstract boolean isSaddled();

    private RNGStreamGenerator rngStreamGenerator;

    public PigEntityMixin(World world) {
        super(world);
    }

    @Redirect(method = "dropLoot", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I"))
    private int dropStandardizedLoot(Random instance, int bound) {
        World overWorld = ((ServerWorld)this.world).getServer().getWorld();
        rngStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRngStreamGenerator();
        return new Random(rngStreamGenerator.getAndUpdateSeed("porkChopSeed")).nextInt(bound);
    }
}
