package net.pixfumy.racemod.mixin.rng.mobspawn;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.MobSpawnerHelper;
import net.minecraft.entity.SpawnEntry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.Weighting;
import net.minecraft.world.World;
import net.pixfumy.racemod.RNGStreamGenerator;
import net.pixfumy.racemod.mixinterface.ILevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Mixin(MobSpawnerHelper.class)
public class TowerSpawnStandardizationMixin {

    @Unique
    private Long prevSeed = null;
    @Unique
    private int spawnCount = 0;
    @Unique
    private SpawnEntry spawnTarget = null;
    @Unique
    private final Random currentRand = new Random();

    @Inject(method = "tickSpawners", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/MobSpawnerHelper;canSpawnAt(Lnet/minecraft/entity/EntityCategory;Lnet/minecraft/world/World;III)Z"))
    private void onPickMob(ServerWorld spawnAnimals, boolean spawnMonsters, boolean bl, boolean par4, CallbackInfoReturnable<Integer> cir, @Local(argsOnly = true) ServerWorld world, @Local EntityCategory category, @Local LocalRef<SpawnEntry> spawnEntryLocalRef) {
        if (shouldTowerStandardizeWork(world, category)) {
            if (spawnCount > 0) {
                spawnEntryLocalRef.set(spawnTarget);
            } else spawnEntryLocalRef.set(null);
        }
    }

    @WrapOperation(method = "tickSpawners", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;method_2136(Lnet/minecraft/entity/EntityCategory;III)Lnet/minecraft/entity/SpawnEntry;"))
    private SpawnEntry onPickCount(ServerWorld instance, EntityCategory category, int x, int y, int z, Operation<SpawnEntry> original, @Local(argsOnly = true) ServerWorld world) {
        if (!shouldTowerStandardizeWork(world, category)) {
            return original.call(instance, category, x, y, z);
        }

        World overWorld = world.getServer().getWorld();
        RNGStreamGenerator rngStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRngStreamGenerator();

        if (prevSeed != null) {
            rngStreamGenerator.setSeed("towerSeed", prevSeed);
        } else {
            prevSeed = rngStreamGenerator.getSeed("towerSeed");
        }
        currentRand.setSeed(prevSeed);

        List var5 = instance.getChunkProvider().method_3865(category, x, y, z);
        SpawnEntry result = var5 != null && !var5.isEmpty() ? (SpawnEntry) Weighting.getRandom(currentRand, var5) : null;
        spawnCount = currentRand.nextInt(4) + 1;
        spawnTarget = result;
        if (result == null || result.type == SlimeEntity.class) {
            rngStreamGenerator.getAndUpdateSeed("towerSeed");
            prevSeed = null;
            spawnTarget = null;
        }
//        if (spawnTarget != null) LogManager.getLogger().info("Selected {} with {}", spawnTarget.type.getSimpleName(), spawnCount);
        return result;
    }

    @Inject(method = "tickSpawners", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    private void onSpawned(ServerWorld world, boolean spawnAnimals, boolean spawnMonsters, boolean bl, CallbackInfoReturnable<Integer> cir, @Local EntityCategory category, @Local MobEntity entity, @Local LocalRef<SpawnEntry> spawnEntryLocalRef) {
        if (shouldTowerStandardizeWork(world, category)) {
            if (prevSeed != null) {
                World overWorld = world.getServer().getWorld();
                RNGStreamGenerator rngStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRngStreamGenerator();
                rngStreamGenerator.getAndUpdateSeed("towerSeed");
            }

            if (--spawnCount <= 0) {
                spawnTarget = null;
                spawnEntryLocalRef.set(null);
            }

//            LogManager.getLogger().info("Spawned {}", EntityType.getEntityName(entity));
        }
        prevSeed = null;
    }

    @Unique
    private boolean shouldTowerStandardizeWork(ServerWorld world, EntityCategory category) {
        if (category != EntityCategory.MONSTER) return false;
        for (Object object : world.playerEntities) {
            PlayerEntity player = (PlayerEntity) object;
            if (player.y > 128) return true;
        }
        return false;
    }
}
