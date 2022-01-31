package io.github.marinersfan824.racemod.mixin;

import io.github.marinersfan824.racemod.ISpiralStaircase;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.structure.StrongholdPieces$StartPiece")
public class StartPieceMixin {
    @Inject(method="getCenterBlockPos",at=@At("HEAD"),cancellable = true)
    private void getCenterBlockPos(CallbackInfoReturnable<BlockPos> cir){
        BlockPos blockPos = ((ISpiralStaircase)(Object)this).getPortalRoomPos();
        if(blockPos!=null){
            cir.setReturnValue(blockPos);
        }
    }
}
