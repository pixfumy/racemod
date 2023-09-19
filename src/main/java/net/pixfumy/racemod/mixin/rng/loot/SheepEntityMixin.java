package net.pixfumy.racemod.mixin.rng.loot;

import net.pixfumy.racemod.RNGStreamGenerator;
import net.pixfumy.racemod.mixinterface.ILevelProperties;
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
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends AnimalEntity {
    private RNGStreamGenerator rngStreamGenerator;

    public SheepEntityMixin(World world) {
        super(world);
    }

    @Redirect(method = "method_2537", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I"))
    public int dropStandardizedLoot(Random instance, int bound) {
        World overWorld = ((ServerWorld)this.world).getServer().getWorld();
        rngStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRngStreamGenerator();
        return new Random(rngStreamGenerator.getAndUpdateSeed("woolSeed")).nextInt(bound);
    }
}
