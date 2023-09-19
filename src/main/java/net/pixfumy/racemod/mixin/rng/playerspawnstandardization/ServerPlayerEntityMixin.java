package net.pixfumy.racemod.mixin.rng.playerspawnstandardization;

import net.pixfumy.racemod.Main;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Shadow public abstract ServerWorld getServerWorld();

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 0))
    private int setSpawnPosX(Random instance, int bound) {
        if (Main.shouldSetPlayerSpawn) {
            return new Random(this.getServerWorld().getSeed() ^ 8739208629771456548L).nextInt(bound);
        }
        return instance.nextInt(bound);
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 1))
    private int setSpawnPosZ(Random instance, int bound) {
        if (Main.shouldSetPlayerSpawn) {
            Main.shouldSetPlayerSpawn = false;
            return new Random(this.getServerWorld().getSeed() ^ 8739208629771456548L).nextInt(bound);
        }
        return instance.nextInt(bound);
    }
}
