package io.github.marinersfan824.racemod.mixin.worldgen.temple;

import net.minecraft.class_5;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

/**
 * @author KingContaria
 */
@Mixin(class_5.class)
public class IronWeightMixin {
    @ModifyArg(
        method = {"<clinit>"},
        at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/util/WeightedRandomChestContent;<init>(Lnet/minecraft/item/Item;IIII)V",
                ordinal = 0
        ),
        index = 4,
        slice = @Slice(
                from = @At(
                        value = "FIELD",
                        target = "Lnet/minecraft/item/Items;IRON_INGOT:Lnet/minecraft/item/Item;"
                )
        )
    )
    private static int increaseIron(int weight) {
        return 7; // vanilla value is 10
    }

}
