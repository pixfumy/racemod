package net.pixfumy.racemod.mixin.rng.loot;

import net.pixfumy.racemod.RNGStreamGenerator;
import net.pixfumy.racemod.mixinterface.ILevelProperties;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpiderEntity.class)
public abstract class SpiderEntityMixin extends LivingEntity {
    private RNGStreamGenerator rngStreamGenerator;
    public SpiderEntityMixin(World world) {
        super(world);
    }
    @Inject(method = "dropLoot", at = @At("HEAD"), cancellable = true)
    private void dropStandardizedLoot(boolean allowDrops, int lootingMultiplier, CallbackInfo ci) {
        World overWorld = ((ServerWorld)this.world).getServer().getWorld();
        rngStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRngStreamGenerator();
        long seedResult = rngStreamGenerator.getAndUpdateSeed("stringSeed");
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
            ItemStack item = new ItemStack(Items.STRING, 1, 0);
            this.dropItem(item, 1);
        }
        if (allowDrops && seedResult % 3 == 0) {
            this.dropItem(Items.SPIDER_EYE, 1);
        }
        ci.cancel();
    }
}
