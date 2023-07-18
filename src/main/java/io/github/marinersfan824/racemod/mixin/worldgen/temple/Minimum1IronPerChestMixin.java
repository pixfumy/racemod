package io.github.marinersfan824.racemod.mixin.worldgen.temple;

import net.minecraft.class_5;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Items;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.WeightedRandomChestContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(StructurePiece.class)
public class Minimum1IronPerChestMixin {
    @Redirect(method = "method_69", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/WeightedRandomChestContent;generateLoot(Ljava/util/Random;[Lnet/minecraft/util/WeightedRandomChestContent;Lnet/minecraft/inventory/Inventory;I)V"))
    private void minimumOf1Iron(Random random, WeightedRandomChestContent[] pool, Inventory inventory, int items) {
        WeightedRandomChestContent.generateLoot(random, pool, inventory, items);
        if ((Object)this instanceof class_5) {
            WeightedRandomChestContent.generateLoot(random, new WeightedRandomChestContent[]{new WeightedRandomChestContent(Items.IRON_INGOT, 0, 1, 1, 1)}, inventory, 1);
        }
    }
}
