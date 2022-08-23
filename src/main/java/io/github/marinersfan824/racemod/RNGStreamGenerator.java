package io.github.marinersfan824.racemod;

import com.google.common.collect.Maps;
import io.github.marinersfan824.racemod.mixinterface.ILevelProperties;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class RNGStreamGenerator {
    private HashMap<String, Long> rngSeeds = new HashMap();

    public void init(long worldSeed) {
        /*  abstracts some of the standardization into this HashMap. To add a new standardized source of RNG, put your entries
            into this map, use your world's RNGStreamGenerator in a mixin, and then update tellPlayerCurrentRates if you want to.
        */
        rngSeeds = new HashMap() {
            {
            //  the first 3 seeds use the same XOR salt as sharpie did in the original forge racemod FeelsStrongMan
                put("blazeRodSeed", worldSeed ^ 64711520272L);
                put("enderPearlSeed", worldSeed ^ 286265360L);
                put("featherSeed", worldSeed ^ 1229781838466121744L);
                put("enderEyeSeed", worldSeed ^ 0x99A2B75BBL);
                put("flintSeed", worldSeed ^ 0xF110301001B2L);
                put("stringSeed", worldSeed ^ 0x120012034131L);
                put("woolSeed", worldSeed ^ 0x7F12468038952L);
                put("porkChopSeed", worldSeed ^ 64182641824614L);
                put("beefSeed", worldSeed ^ 0xFF97FD1823L);
            }
        };
    }

    public long getSeed(String id) {
        if (rngSeeds.containsKey(id)) {
            return rngSeeds.get(id);
        }
        return -1;
    }

    public long getAndUpdateSeed(String id) {
        if (rngSeeds.containsKey(id)) {
            long oldSeed = rngSeeds.get(id);
            Random random = new Random(oldSeed);
            long ret = Math.abs((int)oldSeed) % (int)Math.pow(16.0D, 4.0D);
            rngSeeds.put(id, random.nextLong());
            return ret;
        }
        return -1;
    }

    public void setSeed(String id, long seed) {
        rngSeeds.put(id, seed);
    }

    public Set<Map.Entry<String, Long>> entrySet() {
        return rngSeeds.entrySet();
    }

    /*
    Unfortunately this code is still ugly because each RNG source does its own independent calculations
    with the seed.
     */
    public static void tellPlayerCurrentRates(World world) {
        long seed = world.getSeed();
        RNGStreamGenerator dummy = new RNGStreamGenerator();
        RNGStreamGenerator main = ((ILevelProperties)world.getLevelProperties()).getRngStreamGenerator();
        dummy.blazeRodSeed = main.blazeRodSeed;
        dummy.enderEyeSeed = main.enderEyeSeed;
        dummy.enderPearlSeed = main.enderPearlSeed;
        dummy.featherSeed = main.featherSeed;
        dummy.flintSeed = main.flintSeed;
        dummy.stringSeed = main.stringSeed;
        int total_blazerods = 0;
        int total_blazes = 0;
        int total_pearls = 0;
        int total_endermen = 0;
        int broken_eyes = 0;
        int total_eyes = 0;
        int total_feathers = 0;
        int total_chickens = 0;
        int total_flint = 0;
        int total_gravel = 0;
        int total_string = 0;
        int total_spiders = 0;
        while (total_blazerods < 7) {
            int seedResult = (int)dummy.updateAndGetBlazeRodSeed();
            boolean didPass = (seedResult % 16 < 8);
            if (didPass) {
                total_blazerods++;
            }
            total_blazes++;
        }
        while (total_pearls < 14) {
            int seedResult = (int)dummy.updateAndGetEnderPearlSeed();
            boolean didPass = (seedResult % 16 < 10);
            if (didPass) {
                total_pearls++;
            }
            total_endermen++;
        }
        while (total_eyes < 5) {
            int seedResult = (int)dummy.updateAndGetEnderEyeSeed();
            boolean didPass = (seedResult % 5 > 0);
            if (!didPass) {
                broken_eyes++;
            }
            total_eyes++;
        }
        while (total_feathers < 6) {
            int seedResult = (int) dummy.updateAndGetFeatherSeed();
            for (int i = 0; i < 2; i++) {
                if (total_feathers == 6) {
                    break;
                }
                boolean didPass = (seedResult % 16 < 8);
                if (didPass) {
                    total_feathers++;
                }
                seedResult /= 16;
            }
            total_chickens++;
        }
        while (total_flint < 6) {
            int seedResult = (int) dummy.updateAndGetFlintSeed();
            if (seedResult % 10 == 0) {
                total_flint++;
            }
            total_gravel++;
        }
        while (total_string < 3) {
            int seedResult = (int) dummy.updateAndGetStringSeed();
            for (int i = 0; i < 2; i++) {
                if (total_string == 3) {
                    break;
                }
                boolean didPass = (seedResult % 16 < 8);
                if (didPass) {
                    total_string++;
                }
                seedResult /= 16;
            }
            total_spiders++;
        }
        ClientPlayerEntity player = MinecraftClient.getInstance().field_3805;
        player.addMessage(new TranslatableText(String.format("Current rates on this seed: Blaze rates are %d/%d, Endermen "
                        + "rates are %d/%d, eye breaks are %d/%d, feather rates are %d/%d, flint rates are %d/%d, string rates are %d/%d",
                total_blazerods, total_blazes, total_pearls, total_endermen, broken_eyes, total_eyes, total_feathers, total_chickens, total_flint, total_gravel,
                total_string, total_spiders
        )));
        world.playSound(player.x, player.y, player.z, "ambient.weather.thunder", 10000.0F, 0.8F + 0.2F);
    }
}
