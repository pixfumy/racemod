package io.github.marinersfan824.racemod.mixin.rng;

import io.github.marinersfan824.racemod.mixinterface.ILevelProperties;
import io.github.marinersfan824.racemod.RNGStreamGenerator;
import net.minecraft.block.Blocks;
import net.minecraft.block.GravelBlock;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(GravelBlock.class)
public class FlintMixin {
    private RNGStreamGenerator rngStreamGenerator;
    @Inject(method = "method_398", at = @At("RETURN"), cancellable = true)
    private void getDrops(int i, Random random, int j, CallbackInfoReturnable<Item> cir) {
        if (j > 3) {
            j = 3;
        }
        /* I don't like having to do this, but Block does not store a reference to the world it belongs to,
         and injecting into the method that has the World passed in as a parameter led to some other ugliness.
         Instead, let's call MinecraftServer.getServer().getWorld(). */
        rngStreamGenerator = ((ILevelProperties)(MinecraftServer.getServer().getWorld().getLevelProperties())).getRngStreamGenerator();
        int seedResult = (int) rngStreamGenerator.updateAndGetFlintSeed();
        cir.setReturnValue(seedResult % (10 - j * 3) == 0 ? Items.FLINT : Item.fromBlock(Blocks.GRAVEL));
    }
}
