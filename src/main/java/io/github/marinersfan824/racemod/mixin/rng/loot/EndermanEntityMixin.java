package io.github.marinersfan824.racemod.mixin.rng.loot;

import io.github.marinersfan824.racemod.RNGStreamGenerator;
import io.github.marinersfan824.racemod.mixinterface.ILevelProperties;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndermanEntity.class)
public abstract class EndermanEntityMixin extends LivingEntity {
    private RNGStreamGenerator rngStreamGenerator;
    public EndermanEntityMixin(World world) {
        super(world);
    }

    @Inject(method = "dropLoot", at = @At("HEAD"), cancellable = true)
    private void dropStandardizedLoot(boolean allowDrops, int lootingMultiplier, CallbackInfo ci) {
        World overWorld = ((ServerWorld)this.world).getServer().getWorld();
        rngStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRngStreamGenerator();
        long seedResult = rngStreamGenerator.getAndUpdateSeed("enderPearlSeed");
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
            this.dropItem(item, 1);
        }
        ci.cancel();
    }
}

