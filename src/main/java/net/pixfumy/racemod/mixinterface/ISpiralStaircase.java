package net.pixfumy.racemod.mixinterface;

import net.minecraft.util.math.BlockBox;

public interface ISpiralStaircase {
    void setPortalRoomBox(BlockBox boundingBox);
    BlockBox getPortalRoomBox();
}
