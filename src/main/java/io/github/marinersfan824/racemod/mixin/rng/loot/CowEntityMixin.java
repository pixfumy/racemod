package io.github.marinersfan824.racemod.mixin.rng.loot;

import io.github.marinersfan824.racemod.RNGStreamGenerator;
import io.github.marinersfan824.racemod.mixinterface.ILevelProperties;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CowEntity.class)
public abstract class CowEntityMixin extends LivingEntity {

    private RNGStreamGenerator rngStreamGenerator;

    public CowEntityMixin(World world) {
        super(world);
    }

    @Inject(method = "dropLoot", at = @At("HEAD"), cancellable = true)
    private void dropStandardizedLoot(boolean allowDrops, int lootingMultiplier, CallbackInfo ci) {

        int var3 = this.random.nextInt(3) + this.random.nextInt(1 + lootingMultiplier);
        int var4;
        for(var4 = 0; var4 < var3; ++var4) {
            this.dropItem(Items.LEATHER, 1);
        }
        World overWorld = ((ServerWorld)this.world).getServer().getWorld();
        rngStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRngStreamGenerator();
        long seedResult = rngStreamGenerator.getAndUpdateSeed("beefSeed");
        int numDrops = 1 + (int) (seedResult % (3 + lootingMultiplier));
        for(var4 = 0; var4 < numDrops; ++var4) {
            if (this.isOnFire()) {
                this.dropItem(Items.COOKED_BEEF, 1);
            } else {
                this.dropItem(Items.BEEF, 1);
            }
        }
        ci.cancel();
    }
}
