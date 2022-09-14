package io.github.marinersfan824.racemod.mixinterface;

import net.minecraft.util.math.BlockBox;

public interface ISpiralStaircase {
    void setPortalRoomBox(BlockBox boundingBox);
    BlockBox getPortalRoomBox();
}
