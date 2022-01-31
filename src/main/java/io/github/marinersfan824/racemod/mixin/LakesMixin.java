package io.github.marinersfan824.racemod.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.LakesFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(LakesFeature.class)
public class LakesMixin {
    @Shadow private Block field_7529;

    /**
     * @author marinersfan824
     * @reason remove underground lava and water lakes
     */
    @Overwrite
    public boolean method_4028(World world, Random random, int i, int j, int k) {
        if (j <= 60) {
            return false;
        } else {
            j -= 4;
            boolean[] var6 = new boolean[2048];
            int var7 = random.nextInt(4) + 4;

            int var8;
            for(var8 = 0; var8 < var7; ++var8) {
                double var9 = random.nextDouble() * 6.0D + 3.0D;
                double var11 = random.nextDouble() * 4.0D + 2.0D;
                double var13 = random.nextDouble() * 6.0D + 3.0D;
                double var15 = random.nextDouble() * (16.0D - var9 - 2.0D) + 1.0D + var9 / 2.0D;
                double var17 = random.nextDouble() * (8.0D - var11 - 4.0D) + 2.0D + var11 / 2.0D;
                double var19 = random.nextDouble() * (16.0D - var13 - 2.0D) + 1.0D + var13 / 2.0D;

                for(int var21 = 1; var21 < 15; ++var21) {
                    for(int var22 = 1; var22 < 15; ++var22) {
                        for(int var23 = 1; var23 < 7; ++var23) {
                            double var24 = ((double)var21 - var15) / (var9 / 2.0D);
                            double var26 = ((double)var23 - var17) / (var11 / 2.0D);
                            double var28 = ((double)var22 - var19) / (var13 / 2.0D);
                            double var30 = var24 * var24 + var26 * var26 + var28 * var28;
                            if (var30 < 1.0D) {
                                var6[(var21 * 16 + var22) * 8 + var23] = true;
                            }
                        }
                    }
                }
            }

            int var10;
            int var32;
            boolean var33;
            for(var8 = 0; var8 < 16; ++var8) {
                for(var32 = 0; var32 < 16; ++var32) {
                    for(var10 = 0; var10 < 8; ++var10) {
                        var33 = !var6[(var8 * 16 + var32) * 8 + var10] && (var8 < 15 && var6[((var8 + 1) * 16 + var32) * 8 + var10] || var8 > 0 && var6[((var8 - 1) * 16 + var32) * 8 + var10] || var32 < 15 && var6[(var8 * 16 + var32 + 1) * 8 + var10] || var32 > 0 && var6[(var8 * 16 + (var32 - 1)) * 8 + var10] || var10 < 7 && var6[(var8 * 16 + var32) * 8 + var10 + 1] || var10 > 0 && var6[(var8 * 16 + var32) * 8 + (var10 - 1)]);
                        if (var33) {
                            Material var12 = world.method_3774(i + var8, j + var10, k + var32).getMaterial();
                            if (var10 >= 4 && var12.isFluid()) {
                                return false;
                            }

                            if (var10 < 4 && !var12.hasCollision() && world.method_3774(i + var8, j + var10, k + var32) != this.field_7529) {
                                return false;
                            }
                        }
                    }
                }
            }

            for(var8 = 0; var8 < 16; ++var8) {
                for(var32 = 0; var32 < 16; ++var32) {
                    for(var10 = 0; var10 < 8; ++var10) {
                        if (var6[(var8 * 16 + var32) * 8 + var10]) {
                            world.method_4721(i + var8, j + var10, k + var32, var10 >= 4 ? Blocks.AIR : this.field_7529, 0, 2);
                        }
                    }
                }
            }

            for(var8 = 0; var8 < 16; ++var8) {
                for(var32 = 0; var32 < 16; ++var32) {
                    for(var10 = 4; var10 < 8; ++var10) {
                        if (var6[(var8 * 16 + var32) * 8 + var10] && world.method_3774(i + var8, j + var10 - 1, k + var32) == Blocks.DIRT && world.method_3667(LightType.SKY, i + var8, j + var10, k + var32) > 0) {
                            Biome var34 = world.method_3773(i + var8, k + var32);
                            if (var34.field_7204 == Blocks.MYCELIUM) {
                                world.method_4721(i + var8, j + var10 - 1, k + var32, Blocks.MYCELIUM, 0, 2);
                            } else {
                                world.method_4721(i + var8, j + var10 - 1, k + var32, Blocks.GRASS, 0, 2);
                            }
                        }
                    }
                }
            }

            if (this.field_7529.getMaterial() == Material.LAVA) {
                for(var8 = 0; var8 < 16; ++var8) {
                    for(var32 = 0; var32 < 16; ++var32) {
                        for(var10 = 0; var10 < 8; ++var10) {
                            var33 = !var6[(var8 * 16 + var32) * 8 + var10] && (var8 < 15 && var6[((var8 + 1) * 16 + var32) * 8 + var10] || var8 > 0 && var6[((var8 - 1) * 16 + var32) * 8 + var10] || var32 < 15 && var6[(var8 * 16 + var32 + 1) * 8 + var10] || var32 > 0 && var6[(var8 * 16 + (var32 - 1)) * 8 + var10] || var10 < 7 && var6[(var8 * 16 + var32) * 8 + var10 + 1] || var10 > 0 && var6[(var8 * 16 + var32) * 8 + (var10 - 1)]);
                            if (var33 && (var10 < 4 || random.nextInt(2) != 0) && world.method_3774(i + var8, j + var10, k + var32).getMaterial().hasCollision()) {
                                world.method_4721(i + var8, j + var10, k + var32, Blocks.STONE, 0, 2);
                            }
                        }
                    }
                }
            }

            if (this.field_7529.getMaterial() == Material.WATER) {
                for(var8 = 0; var8 < 16; ++var8) {
                    for(var32 = 0; var32 < 16; ++var32) {
                        byte var35 = 4;
                        if (world.method_3730(i + var8, j + var35, k + var32)) {
                            world.method_4721(i + var8, j + var35, k + var32, Blocks.ICE, 0, 2);
                        }
                    }
                }
            }

            return true;
            }
        }
}
