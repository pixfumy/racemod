package net.pixfumy.racemod.mixin.dragon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.vecmath.Vector3d;
import java.util.ArrayDeque;
import java.util.Random;

@Mixin(EnderDragonEntity.class)
public abstract class StandardizeDragonRNGMixin extends Entity {
    @Shadow private Entity target;
    @Shadow public double field_3742;
    @Shadow public double field_3751;
    @Shadow public double field_3752;
    @Unique
    private Random chargeRandom;

    @Unique
    private Random targetBlockRandom;

    @Unique
    private Random gaussianRandom;

    public StandardizeDragonRNGMixin(World world) {
        super(world);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initInject(CallbackInfo ci) {
        if (!world.isClient) {
            this.random.setSeed(((ServerWorld)world).getServer().getWorld().getSeed() ^ 4491673419436809842L);
            this.chargeRandom = new Random(((ServerWorld)world).getServer().getWorld().getSeed() ^ 44916734842L);
            this.targetBlockRandom = new Random(((ServerWorld)world).getServer().getWorld().getSeed() ^ 987987921734908L);
            this.gaussianRandom = new Random(((ServerWorld)world).getServer().getWorld().getSeed() ^ 5348404944389266319L);
        }
    }

    @Redirect(method = "method_2906", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 0))
    private int pickTarget(Random instance, int i) {
        for (Object object : world.playerEntities) {
            PlayerEntity player = (PlayerEntity) object;
            double distanceSquared = player.squaredDistanceTo(x, y, z);

            if (distanceSquared <= 15 * 15) {
                return 1;
            }
        }
        return chargeRandom.nextInt(2);
    }

    @Redirect(method = "method_2906", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F"))
    private float standardizeTargetBlockChoice(Random instance) {
        return targetBlockRandom.nextFloat();
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextGaussian()D"))
    private double stopGaussian(Random instance) {
        if (this.ticksAlive % 5 == 0) {
            return gaussianRandom.nextGaussian();
        }
        return 0;
    }
}
