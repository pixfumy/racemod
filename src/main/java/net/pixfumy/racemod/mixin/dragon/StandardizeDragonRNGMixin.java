package net.pixfumy.racemod.mixin.dragon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.damage.DamageSource;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.vecmath.Vector3d;
import java.util.Random;

@Mixin(EnderDragonEntity.class)
/**
 * Lots of hacky logic in this mixin. Standardizing the major events (charge chance, target block, gaussian sway) gets you 90% of the way there,
 * but then we also handle some edge cases that arise through player RNG.
 */
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

    @Unique
    private long lastTargetBlockTick = 0;

    @Unique
    private Vector3d lastTargetBlock;

    @Unique
    private DamageSource lastDamageSource;

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

    /* These next 3 methods handle the case where the dragon targets the player several times in a row after a bed hit.
        We don't want to "skip charges" by advancing the RNG stream in this case. Force the dragon to pick a block without touching chargeRandom. */

    // save the damageSource as is
    @Inject(method = "setAngry", at = @At("HEAD"))
    private void saveLastDamageSource(EnderDragonPart enderDragonPart, DamageSource damageSource, float angry, CallbackInfoReturnable<Boolean> cir) {
        lastDamageSource = damageSource;
    }

    /* EXCEPT in the case where it was a crystal bait. We can now check if it was a crystal bait or arrow hit using
       lastDamageSource.isProjectile() */
    @Inject(method = "tickWithEndCrystals", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/boss/dragon/EnderDragonEntity;setAngry(Lnet/minecraft/entity/boss/dragon/EnderDragonPart;Lnet/minecraft/entity/damage/DamageSource;F)Z",
            shift = At.Shift.AFTER))
    private void excludeEndCrystalBaits(CallbackInfo ci) {
        lastDamageSource.setProjectile();
    }

    @Redirect(method = "method_2906", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 0))
    private int pickTarget(Random instance, int i) {
        for (Object object : world.playerEntities) {
            PlayerEntity player = (PlayerEntity) object;
            double distanceSquared = player.squaredDistanceTo(x, y, z);
            // hacky, but this handles arrow baits and crystals when the dragon is nearby
            if (lastDamageSource != null && lastDamageSource.isProjectile()) {
                lastDamageSource = null;
                break;
            }

            // 15 is a safe cutoff for beds, crystal explosions, close arrows, and ofc sword hits
            if (distanceSquared <= 15 * 15) {
                return 1;
            }
        }
        return chargeRandom.nextInt(2);
    }

    /* these next 4 injects handle the edge case where the dragon charges the player, resets its target to a block, and THEN the player does a bed hit.
     It's preferred to not reset the target block in this case. 10 ticks was tested to be a safe cutoff for this scenario.*/
    @Inject(method = "method_2906", at = @At("TAIL"))
    private void saveLastBlockTargetTick(CallbackInfo ci) {
        if (this.target == null) {
            lastTargetBlockTick = ticksAlive;
            lastTargetBlock = new Vector3d(field_3742, field_3751, field_3752);
        }
    }

    @Inject(method = "setAngry", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/boss/dragon/EnderDragonEntity;field_3742:D", shift = At.Shift.AFTER))
    private void dontResetTargetBlockX(EnderDragonPart multipart, DamageSource source, float angry, CallbackInfoReturnable<Boolean> cir) {
        lastDamageSource = source;
        if (lastTargetBlockTick != 0 && ticksAlive - lastTargetBlockTick < 10) {
            field_3742 = lastTargetBlock.x;
        }
    }

    @Inject(method = "setAngry", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/boss/dragon/EnderDragonEntity;field_3751:D", shift = At.Shift.AFTER))
    private void dontResetTargetBlockY(CallbackInfoReturnable<Boolean> cir) {
        if (lastTargetBlockTick != 0 && ticksAlive - lastTargetBlockTick < 10) {
            field_3751 = lastTargetBlock.y;
        }
    }

    @Inject(method = "setAngry", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/boss/dragon/EnderDragonEntity;field_3752:D", shift = At.Shift.AFTER))
    private void dontResetTargetBlockZ(CallbackInfoReturnable<Boolean> cir) {
        if (lastTargetBlockTick != 0 && ticksAlive - lastTargetBlockTick < 10) {
            field_3752 = lastTargetBlock.z;
        }
    }
}
