package net.pixfumy.racemod.mixin.rng.loot;

import net.pixfumy.racemod.RNGStreamGenerator;
import net.pixfumy.racemod.mixinterface.ILevelProperties;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(CowEntity.class)
public abstract class CowEntityMixin extends LivingEntity {

    private RNGStreamGenerator rngStreamGenerator;

    public CowEntityMixin(World world) {
        super(world);
    }

    @Redirect(method = "dropLoot", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I"),
            slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 2)))
    public int dropStandardizedLoot(Random instance, int bound) {
        World overWorld = ((ServerWorld)this.world).getServer().getWorld();
        rngStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRngStreamGenerator();
        return new Random(rngStreamGenerator.getAndUpdateSeed("beefSeed")).nextInt(bound);
    }
}
