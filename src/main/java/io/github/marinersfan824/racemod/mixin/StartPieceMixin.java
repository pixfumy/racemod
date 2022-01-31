package io.github.marinersfan824.racemod.mixin;

import io.github.marinersfan824.racemod.ISpiralStaircase;
import net.minecraft.class_27;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(class_27.class)
public class StartPieceMixin {
    @Inject(method="method_51",at=@At("HEAD"),cancellable = true)
    private void getCenterVec3i(CallbackInfoReturnable<Vec3i> cir){
        Vec3i Vec3i = ((ISpiralStaircase)(Object)this).getPortalRoomPos();
        if(Vec3i!=null){
            cir.setReturnValue(Vec3i);
        }
    }
}
