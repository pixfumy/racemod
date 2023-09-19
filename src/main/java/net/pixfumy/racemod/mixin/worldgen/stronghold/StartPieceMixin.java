package net.pixfumy.racemod.mixin.worldgen.stronghold;

import net.pixfumy.racemod.mixinterface.ISpiralStaircase;
import net.minecraft.structure.class_27;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(class_27.class)
public class StartPieceMixin {
    @Inject(method="method_51",at=@At("HEAD"),cancellable = true)
    private void getCenterVec3i(CallbackInfoReturnable<Vec3i> cir){
        BlockBox portalRoomBox = ((ISpiralStaircase) (Object) this).getPortalRoomBox();
        if(portalRoomBox!=null){
            Vec3i Vec3i = new Vec3i(portalRoomBox.getCenterX(),portalRoomBox.getCenterY(),portalRoomBox.getCenterZ());
            cir.setReturnValue(Vec3i);
        }
    }
}
