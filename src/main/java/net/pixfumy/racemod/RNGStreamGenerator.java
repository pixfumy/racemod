package net.pixfumy.racemod;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class RNGStreamGenerator {
    protected HashMap<String, Long> rngSeeds = new HashMap();

    public void init(long worldSeed) {
        /*  abstracts some of the standardization into this HashMap. To add a new standardized source of RNG, put your entries
            into this map, use your world's RNGStreamGenerator in a mixin, and then update SeedfinderUtil.tellPlayerCurrentRates if you want to.
        */
        rngSeeds = new HashMap() {
            {
            //  the first 3 seeds use the same XOR salt as sharpie did in the original forge racemod FeelsStrongMan
                put("blazeRodSeed", worldSeed ^ 64711520272L);
                put("enderPearlSeed", worldSeed ^ 286265360L);
                put("featherSeed", worldSeed ^ 1229781838466121744L);
                put("blazeSpawnSeed", worldSeed ^ 0x5F7E6D8C9B0A1B2CL);
                put("enderEyeSeed", worldSeed ^ 0x99A2B75BBL);
                put("flintSeed", worldSeed ^ 0xF110301001B2L);
                put("stringSeed", worldSeed ^ 0x120012034131L);
                put("woolSeed", worldSeed ^ 0x7F12468038952L);
                put("porkChopSeed", worldSeed ^ 64182641824614L);
                put("beefSeed", worldSeed ^ 0xFF97FD1823L);
                put("arrowSeed", worldSeed ^ 0x9A2B3C4D5E6F7A8BL);
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

    public Set<String> keySet() {
        return rngSeeds.keySet();
    }
}
