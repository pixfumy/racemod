package net.pixfumy.racemod.mixin.worldgen.stronghold;

import net.pixfumy.racemod.mixinterface.ISpiralStaircase;
import net.minecraft.structure.class_26;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(class_26.class)
public class SpiralStaircaseMixin implements ISpiralStaircase {
    @Shadow private boolean field_49;
    public BlockBox portalRoomBox;

    public void setPortalRoomBox(BlockBox box){
        portalRoomBox = box;
    }

    @Override
    public BlockBox getPortalRoomBox() {
        return portalRoomBox;
    }

    @Inject(method="serialize",at=@At("TAIL"))
    private void serializePortalRoomPos(NbtCompound structureNbt, CallbackInfo ci){
        if(this.field_49&&this.portalRoomBox !=null){
            structureNbt.putIntArray("portalRoomPos",new int[]{portalRoomBox.getCenterX(), portalRoomBox.getCenterY(), portalRoomBox.getCenterZ()});
        }
    }

    @Inject(method="deserialize",at=@At("TAIL"))
    private void deserializePortalRoomPos(NbtCompound structureNbt, CallbackInfo ci){
        if(this.field_49){
            int[] pos = structureNbt.getIntArray("portalRoomPos");
            if(pos.length==3){
                this.portalRoomBox = new BlockBox(pos[0],pos[1],pos[2],pos[0],pos[1],pos[2]);
            }
        }
    }
}
