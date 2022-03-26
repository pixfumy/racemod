package io.github.marinersfan824.racemod.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.MobSpawnerHelper;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HostileEntity.class)
public class TempleMobSpawnMixin {
    @Inject(method = "method_3087", at = @At("HEAD"), cancellable = true)
    private void checkIfInTemple(CallbackInfoReturnable<Boolean> cir) {
        Entity thisEntity = (Entity) (Object) this;
        int x = (int) thisEntity.x;
        int y = (int) thisEntity.y;
        int z = (int) thisEntity.z;
        Block standingBlock = thisEntity.world.method_3774(x, y-1, z);
        Chunk chunk = thisEntity.world.getChunk(x >> 4, z >> 4);
        x &= 15;
        z &= 15;
        ChunkSection chunkSection = ((ChunkSectionAccessor)chunk).getChunkSections()[y >> 4];
        if (chunkSection != null && standingBlock == Blocks.SANDSTONE) {
            int skylight = chunkSection.getSkyLight(x, y & 15, z);
            if (skylight != 15) {
                cir.setReturnValue(false);
            }
        }
    }
}
