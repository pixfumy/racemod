package io.github.marinersfan824.racemod.mixin;

import io.github.marinersfan824.racemod.RNGStreamGenerator;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class BedrockDropMixin {
    @Inject(method = "dropStack", at = @At("HEAD"))
    public void dropItemOverride(ItemStack stack, boolean bl, boolean incrementStats, CallbackInfoReturnable<ItemEntity> cir) {
        if (stack.getItem() == Item.fromBlock(Blocks.BEDROCK)) {
            World world = MinecraftClient.getInstance().world;
            RNGStreamGenerator.tellPlayerRates(world);
        }
    }

}
