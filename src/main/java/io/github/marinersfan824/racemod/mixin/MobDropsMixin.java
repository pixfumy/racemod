package io.github.marinersfan824.racemod.mixin;

import io.github.marinersfan824.racemod.ILevelProperties;
import io.github.marinersfan824.racemod.RNGStreamGenerator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(LivingEntity.class)
public abstract class MobDropsMixin {
    @Shadow protected int playerHitTimer;
    @Shadow protected abstract void dropLoot(boolean allowDrops, int lootingMultiplier);

    @Redirect(method = "onKilled", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;dropLoot(ZI)V"))
    protected void dropLoot(LivingEntity entity, boolean allowDrops, int lootingMultiplier) {
        MinecraftServer server = ((ServerWorld)entity.world).getServer();
        RNGStreamGenerator rngStreamGenerator = ((ILevelProperties) server.getWorld().getLevelProperties()).getRngStreamGenerator();
        if (entity instanceof EndermanEntity) {
            int seedResult = (int) rngStreamGenerator.updateAndGetEnderPearlSeed();
            int numRolls = 1 + lootingMultiplier;
            int numDrops = 0;
            int i;
            for (i = 0; i < numRolls; i++) {
                boolean passed = (seedResult % 16 < 10);

                if (passed) {
                    numDrops++;
                }
                seedResult /= 16;
            }
            for (i = 0; i < numDrops; i++) {
                ItemStack item = new ItemStack(Items.ENDER_PEARL, 1, 0);
                entity.dropItem(item, 1);
            }
        } else if (entity instanceof ChickenEntity) {

            int seedResult = (int) rngStreamGenerator.updateAndGetFeatherSeed();
            int numRolls = 2 + lootingMultiplier;
            int numDrops = 0;
            int j;
            for (j = 0; j < numRolls; j++) {
                boolean passed = (seedResult % 16 < 8);

                if (passed) {
                    numDrops++;
                }
                seedResult /= 16;
            }

            for (j = 0; j < numDrops; j++) {
                ItemStack item = new ItemStack(Items.FEATHER, 1, 0);
                entity.dropItem(item, 1);
            }

            if (entity.isOnFire()) {
                entity.dropItem(Items.COOKED_CHICKEN, 1);
            } else {
                entity.dropItem(Items.CHICKEN, 1);
            }

        } else if (entity instanceof BlazeEntity) {

            int seedResult = (int) rngStreamGenerator.updateAndGetBlazeRodSeed();
            int numRolls = 1 + lootingMultiplier;
            int numDrops = 0;
            int i;

            for (i = 0; i < numRolls; i++) {
                boolean passed = (seedResult % 16 < 8);
                if (passed) {
                    numDrops++;
                }
                seedResult /= 16;
            }

            for (i = 0; i < numDrops; i++) {
                ItemStack item = new ItemStack(Items.BLAZE_ROD, 1, 0);
                entity.dropItem(item, 1);
            }
        } else {
            this.dropLoot(this.playerHitTimer > 0, lootingMultiplier);
        }
    }
}
