package io.github.marinersfan824.racemod.mixin.rng;

import io.github.marinersfan824.racemod.RNGStreamGenerator;
import io.github.marinersfan824.racemod.mixinterface.ILevelProperties;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends LivingEntity {
    private RNGStreamGenerator rngStreamGenerator;

    public ChickenEntityMixin(World world) {
        super(world);
    }

    @Inject(method = "dropLoot", at = @At("HEAD"), cancellable = true)
    private void dropStandardizedLoot(boolean allowDrops, int lootingMultiplier, CallbackInfo ci) {
        World overWorld = ((ServerWorld)this.world).getServer().getWorld();
        rngStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRngStreamGenerator();
        long seedResult = rngStreamGenerator.getAndUpdateSeed("featherSeed");
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
            this.dropItem(item, 1);
        }

        if (this.isOnFire()) {
            this.dropItem(Items.COOKED_CHICKEN, 1);
        } else {
            this.dropItem(Items.CHICKEN, 1);
        }
        ci.cancel();
    }
}
