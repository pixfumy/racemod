package net.pixfumy.racemod.mixin.rng;

import net.pixfumy.racemod.mixinterface.ILevelProperties;
import net.pixfumy.racemod.RNGStreamGenerator;
import net.minecraft.nbt.NbtCompound;
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

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/nbt/NbtCompound;)V")
    public void initInject(NbtCompound worldNbt, CallbackInfo ci) {
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
    private void writeSeedsToNBT(NbtCompound worldNBT, NbtCompound playerNBT, CallbackInfo ci) {
        for (Map.Entry<String, Long> pair: rngStreamGenerator.entrySet()) {
            String id = pair.getKey();
            worldNBT.putLong(id, rngStreamGenerator.getSeed(id));
        }
    }
}
