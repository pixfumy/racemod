package io.github.marinersfan824.racemod.mixin;

import io.github.marinersfan824.racemod.ISpiralStaircase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.structure.StrongholdPieces$SpiralStaircase")
public class SpiralStaircaseMixin implements ISpiralStaircase {
    @Shadow private boolean isStructureStart;
    public BlockPos portalRoomPos;

    public void setPortalRoomPos(BlockPos blockPos){
        portalRoomPos = blockPos;
    }

    @Override
    public BlockPos getPortalRoomPos() {
        return portalRoomPos;
    }

    @Inject(method="serialize",at=@At("TAIL"))
    private void serializePortalRoomPos(CompoundTag structureNbt, CallbackInfo ci){
        if(this.isStructureStart&&this.portalRoomPos!=null){
            structureNbt.putIntArray("portalRoomPos",new int[]{portalRoomPos.getX(),portalRoomPos.getY(),portalRoomPos.getZ()});
        }
    }

    @Inject(method="deserialize",at=@At("TAIL"))
    private void deserializePortalRoomPos(CompoundTag structureNbt, CallbackInfo ci){
        if(this.isStructureStart){
            int[] pos = structureNbt.getIntArray("portalRoomPos");
            if(pos.length==3){
                this.portalRoomPos= new BlockPos(pos[0],pos[1],pos[2]);
            }
        }
    }
}
