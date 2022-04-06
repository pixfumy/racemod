package io.github.marinersfan824.racemod.mixin;

import io.github.marinersfan824.racemod.ILevelProperties;
import io.github.marinersfan824.racemod.RNGStreamGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelProperties.class)
public abstract class LevelPropertiesMixin implements ILevelProperties {
    private final RNGStreamGenerator instance = new RNGStreamGenerator();

    @Shadow private long seed;

    @Override
    public RNGStreamGenerator getRngStreamGenerator(){
        return this.instance;
    }

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V")
    public void initInject(CompoundTag worldNbt, CallbackInfo ci) {
        if (worldNbt.contains("enderEyeSeed")) {
            long enderEyeSeed = worldNbt.getLong("enderEyeSeed");
            if (enderEyeSeed == 0) {
                instance.initializeEyeSeed(this.seed);
            } else {
                instance.setEnderEyeSeed(enderEyeSeed);
            }
        } else {
            instance.initializeEyeSeed(this.seed);
        }
        if (worldNbt.contains("enderPearlSeed")) {
            long enderPearlSeed = worldNbt.getLong("enderPearlSeed");
            if (enderPearlSeed == 0) {
                instance.initializePearlSeed(this.seed);
            } else {
                instance.setEnderPearlSeed(enderPearlSeed);
            }
        } else {
            instance.initializePearlSeed(this.seed);
        }
        if (worldNbt.contains("blazeRodSeed")) {
            long blazeRodSeed = worldNbt.getLong("blazeRodSeed");
            if (blazeRodSeed == 0) {
                instance.initializeBlazeRodSeed(this.seed);
            } else {
                instance.setBlazeRodSeed(blazeRodSeed);
            }
        } else {
            instance.initializeBlazeRodSeed(this.seed);
        }
        if (worldNbt.contains("featherSeed")) {
            long featherSeed = worldNbt.getLong("featherSeed");
            if (featherSeed == 0) {
                instance.initializeFeatherSeed(this.seed);
            } else {
                instance.setFeatherSeed(featherSeed);
            }
        } else {
            instance.initializeFeatherSeed(this.seed);
        }
        if (worldNbt.contains("flintSeed")) {
            long flintSeed = worldNbt.getLong("flintSeed");
            if (flintSeed == 0) {
                instance.initializeFlintSeed(this.seed);
            } else {
                instance.setFlintSeed(flintSeed);
            }
        } else {
            instance.initializeFlintSeed(this.seed);
        }
        if (worldNbt.contains("stringSeed")) {
            long stringSeed = worldNbt.getLong("stringSeed");
            if (stringSeed == 0) {
                instance.initializeStringSeed(this.seed);
            } else {
                instance.setStringSeed(this.seed);
            }
        } else {
            instance.initializeStringSeed(this.seed);
        }
    }

    @Inject(at = @At("HEAD"), method = "putNbt")
    private void writeSeedsToNBT(CompoundTag worldNBT, CompoundTag playerNBT, CallbackInfo ci) {
        worldNBT.putLong("enderEyeSeed", instance.getEnderEyeSeed());
        worldNBT.putLong("enderPearlSeed", instance.getEnderPearlSeed());
        worldNBT.putLong("blazeRodSeed", instance.getBlazeRodSeed());
        worldNBT.putLong("featherSeed", instance.getFeatherSeed());
        worldNBT.putLong("flintSeed", instance.getFlintSeed());
        worldNBT.putLong("stringSeed", instance.getStringSeed());
    }
}
