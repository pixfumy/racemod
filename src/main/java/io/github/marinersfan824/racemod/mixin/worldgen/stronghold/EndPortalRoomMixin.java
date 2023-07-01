package io.github.marinersfan824.racemod.mixin.worldgen.stronghold;

import io.github.marinersfan824.racemod.mixinterface.ISpiralStaircase;
import net.minecraft.class_21;
import net.minecraft.structure.StructurePiece;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;

@Mixin(class_21.class)
public class EndPortalRoomMixin {
    @Inject(method="fillOpenings",at=@At("TAIL"))
    private void onFillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random, CallbackInfo ci){
        if(start!=null){
            ((ISpiralStaircase)start).setPortalRoomBox(((StructurePiece)(Object)this).getBoundingBox());
        }
    }
}
