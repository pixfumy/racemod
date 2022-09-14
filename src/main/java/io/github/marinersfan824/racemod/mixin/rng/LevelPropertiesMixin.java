package io.github.marinersfan824.racemod.mixin.rng;

import io.github.marinersfan824.racemod.mixinterface.ILevelProperties;
import io.github.marinersfan824.racemod.RNGStreamGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LevelProperties.class)
public abstract class LevelPropertiesMixin implements ILevelProperties {
    @Shadow private long seed;
    private final RNGStreamGenerator rngStreamGenerator = new RNGStreamGenerator();
    @Override
    public RNGStreamGenerator getRngStreamGenerator(){
        return this.rngStreamGenerator;
    }

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V")
    public void initInject(CompoundTag worldNbt, CallbackInfo ci) {
        rngStreamGenerator.init(this.seed);
        for (Map.Entry<String, Long> pair: rngStreamGenerator.entrySet()) {
            String id = pair.getKey();
            if (worldNbt.contains(id)) {
                long seed = worldNbt.getLong(id);
                if (seed != 0) {
                    rngStreamGenerator.setSeed(id, seed);
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "putNbt")
    private void writeSeedsToNBT(CompoundTag worldNBT, CompoundTag playerNBT, CallbackInfo ci) {
        for (Map.Entry<String, Long> pair: rngStreamGenerator.entrySet()) {
            String id = pair.getKey();
            worldNBT.putLong(id, rngStreamGenerator.getSeed(id));
        }
    }
}
