package io.github.marinersfan824.racemod.mixin;

import io.github.marinersfan824.racemod.ISpiralStaircase;
import net.minecraft.class_26;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(class_26.class)
public class SpiralStaircaseMixin implements ISpiralStaircase {
    @Shadow private boolean field_49;
    public Vec3i portalRoomPos;

    public void setPortalRoomPos(Vec3i Vec3i){
        portalRoomPos = Vec3i;
    }

    @Override
    public Vec3i getPortalRoomPos() {
        return portalRoomPos;
    }

    @Inject(method="serialize",at=@At("TAIL"))
    private void serializePortalRoomPos(CompoundTag structureNbt, CallbackInfo ci){
        if(this.field_49&&this.portalRoomPos!=null){
            structureNbt.putIntArray("portalRoomPos",new int[]{portalRoomPos.field_4613,portalRoomPos.field_4614,portalRoomPos.field_4615});
        }
    }

    @Inject(method="deserialize",at=@At("TAIL"))
    private void deserializePortalRoomPos(CompoundTag structureNbt, CallbackInfo ci){
        if(this.field_49){
            int[] pos = structureNbt.getIntArray("portalRoomPos");
            if(pos.length==3){
                this.portalRoomPos= new Vec3i(pos[0],pos[1],pos[2]);
            }
        }
    }
}
