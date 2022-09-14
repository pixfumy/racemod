package io.github.marinersfan824.racemod.mixin.rng;

import io.github.marinersfan824.racemod.RNGStreamGenerator;
import io.github.marinersfan824.racemod.mixinterface.ILevelProperties;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends AnimalEntity {
    @Shadow public abstract int method_2866();

    @Shadow public abstract boolean isSheared();

    @Shadow public abstract void setSheared(boolean bl);

    private RNGStreamGenerator rngStreamGenerator;

    public SheepEntityMixin(World world) {
        super(world);
    }

    /*
     * Had to make this an inject instead of a redirect due to some weird mapping error
     */
    @Inject(method = "canBeLeashedBy", at = @At("HEAD"), cancellable = true)
    public void canBeLeashedBy(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        ItemStack var2 = player.inventory.getMainHandStack();
        if (var2 != null && var2.getItem() == Items.SHEARS && !this.isSheared() && !this.isBaby()) {
            if (!this.world.isClient) {
                this.setSheared(true);
                World overWorld = ((ServerWorld)this.world).getServer().getWorld();
                rngStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRngStreamGenerator();
                long seedResult = rngStreamGenerator.getAndUpdateSeed("woolSeed");
                int var3 = 1 + (int) (seedResult % 3);
                for(int var4 = 0; var4 < var3; ++var4) {
                    ItemEntity var5 = this.dropItem(new ItemStack(Item.fromBlock(Blocks.WOOL), 1, this.method_2866()), 1.0F);
                    var5.velocityY += (double)(this.random.nextFloat() * 0.05F);
                    var5.velocityX += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
                    var5.velocityZ += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
                }
            }
            var2.damage(1, (LivingEntity)player);
            this.playSound("mob.sheep.shear", 1.0F, 1.0F);
        }
        cir.setReturnValue(super.canBeLeashedBy(player));
    }
}
