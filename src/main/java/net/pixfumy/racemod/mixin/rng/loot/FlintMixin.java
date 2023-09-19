package net.pixfumy.racemod.mixin.rng.loot;


import net.pixfumy.racemod.mixinterface.ILevelProperties;
import net.pixfumy.racemod.RNGStreamGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.GravelBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GravelBlock.class)
public class FlintMixin extends Block {
    private RNGStreamGenerator rngStreamGenerator;

    protected FlintMixin(Material material) {
        super(material);
    }

    @Override
    public void method_410(World world, int i, int j, int k, int l, float f, int m) {
        if (!world.isClient) {
            int var8 = this.getBonusDrops(m, world.random);
            for (int var9 = 0; var9 < var8; ++var9) {
                if (!(world.random.nextFloat() > f)) {
                    World overWorld = ((ServerWorld) world).getServer().getWorld();
                    rngStreamGenerator = ((ILevelProperties) overWorld.getLevelProperties()).getRngStreamGenerator();
                    long seedResult = rngStreamGenerator.getAndUpdateSeed("flintSeed");
                    Item var10 = seedResult % 10 == 0 ? Items.FLINT : Item.fromBlock(this);
                    if (var10 != null) {
                        this.method_422(world, i, j, k, new ItemStack(var10, 1, this.method_431(l)));
                    }
                }
            }
        }
    }
}
