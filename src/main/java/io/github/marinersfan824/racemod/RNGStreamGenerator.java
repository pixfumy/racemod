package io.github.marinersfan824.racemod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

import java.util.Random;

public class RNGStreamGenerator {
    private long enderEyeSeed;
    private long enderPearlSeed;
    private long blazeRodSeed;
    private long featherSeed;

    public void initializeEyeSeed(long worldSeed) {
        long seed = worldSeed ^ 0x99A2B75BBL;
        Random stream = new Random(seed);
        for (int i = 0; i < 24; i++) {
            seed = stream.nextLong();
        }
        this.enderEyeSeed = seed;
    }

    public void initializeBlazeRodSeed(long worldSeed) {
        long seed = worldSeed ^ 0x1111101101000010L;
        Random stream = new Random(seed);
        for (int i = 0; i < 43; i++) {
            seed = stream.nextLong();
        }
        this.blazeRodSeed = seed;
    }

    public void initializePearlSeed(long worldSeed) {
        long seed = worldSeed ^ 0xF111B7010L;
        Random stream = new Random(seed);
        for (int i = 0; i < 97; i++) {
            seed = stream.nextLong();
        }
        this.enderPearlSeed = seed;
    }

    public void initializeFeatherSeed(long worldSeed) {
        long seed = worldSeed ^ 0x1111101101000010L;
        Random stream = new Random(seed);
        for (int i = 0; i < 43; i++) {
            seed = stream.nextLong();
        }
        this.featherSeed = seed;
    }

    public long updateAndGetEnderEyeSeed() {
        Random stream = new Random(this.enderEyeSeed);
        long ret = Math.abs((int)this.enderEyeSeed) % (int)Math.pow(16.0D, 4.0D);
        this.enderEyeSeed = stream.nextLong();
        return ret;
    }

    public long updateAndGetEnderPearlSeed() {
        Random stream = new Random(this.enderPearlSeed);
        long ret = Math.abs((int)this.enderPearlSeed) % (int)Math.pow(16.0D, 4.0D);
        this.enderPearlSeed = stream.nextLong();
        return ret;
    }

    public long updateAndGetBlazeRodSeed() {
        Random stream = new Random(this.blazeRodSeed);
        long ret = Math.abs((int)this.blazeRodSeed) % (int)Math.pow(16.0D, 4.0D);
        this.blazeRodSeed = stream.nextLong();
        return ret;
    }

    public long updateAndGetFeatherSeed() {
        Random stream = new Random(this.featherSeed);
        long ret = Math.abs((int)this.featherSeed) % (int)Math.pow(16.0D, 5.0D);
        this.featherSeed = stream.nextLong();
        return ret;
    }

    public static void tellPlayerInitialRates(World world) {
        long seed = world.getSeed();
        RNGStreamGenerator dummy = new RNGStreamGenerator();
        dummy.initializeBlazeRodSeed(seed);
        dummy.initializeEyeSeed(seed);
        dummy.initializeFeatherSeed(seed);
        dummy.initializePearlSeed(seed);

        int total_blazerods = 0;
        int total_blazes = 0;
        int total_pearls = 0;
        int total_endermen = 0;
        int broken_eyes = 0;
        int total_eyes = 0;

        while (total_blazerods < 7) {
            int seedResult = Math.abs((int)dummy.updateAndGetBlazeRodSeed());
            boolean didPass = (seedResult % 16 < 8);
            if (didPass) {
                total_blazerods++;
            }
            total_blazes++;
        }
        while (total_pearls < 14) {
            int seedResult = Math.abs((int)dummy.updateAndGetEnderPearlSeed());
            boolean didPass = (seedResult % 16 < 10);
            if (didPass) {
                total_pearls++;
            }
            total_endermen++;
        }
        while (total_eyes < 5) {
            int seedResult = Math.abs((int)dummy.updateAndGetEnderEyeSeed());
            boolean didPass = (seedResult % 5 > 0);
            if (!didPass) {
                broken_eyes++;
            }
            total_eyes++;
        }
        ClientPlayerEntity player = MinecraftClient.getInstance().field_3805;
        player.addMessage(new TranslatableText(String.format("Initial rates on this seed: Blaze rates are %d/%d, Endermen "
                        + "rates are %d/%d, eye breaks are %d/%d",
                total_blazerods, total_blazes, total_pearls, total_endermen, broken_eyes, total_eyes)));
        world.playSound(player.x, player.y, player.z, "fireworks.launch", 10000.0F, 0.8F + 0.2F);
    }

    public static void tellPlayerCurrentRates(World world) {
        long seed = world.getSeed();
        RNGStreamGenerator dummy = new RNGStreamGenerator();
        RNGStreamGenerator main = ((ILevelProperties)world.getLevelProperties()).getRngStreamGenerator();
        dummy.blazeRodSeed = main.blazeRodSeed;
        dummy.enderEyeSeed = main.enderEyeSeed;
        dummy.enderPearlSeed = main.enderPearlSeed;
        dummy.featherSeed = main.featherSeed;
        int total_blazerods = 0;
        int total_blazes = 0;
        int total_pearls = 0;
        int total_endermen = 0;
        int broken_eyes = 0;
        int total_eyes = 0;

        while (total_blazerods < 7) {
            int seedResult = Math.abs((int)dummy.updateAndGetBlazeRodSeed());
            boolean didPass = (seedResult % 16 < 8);
            if (didPass) {
                total_blazerods++;
            }
            total_blazes++;
        }
        while (total_pearls < 14) {
            int seedResult = Math.abs((int)dummy.updateAndGetEnderPearlSeed());
            boolean didPass = (seedResult % 16 < 10);
            if (didPass) {
                total_pearls++;
            }
            total_endermen++;
        }
        while (total_eyes < 5) {
            int seedResult = Math.abs((int)dummy.updateAndGetEnderEyeSeed());
            boolean didPass = (seedResult % 5 > 0);
            if (!didPass) {
                broken_eyes++;
            }
            total_eyes++;
        }
        ClientPlayerEntity player = MinecraftClient.getInstance().field_3805;
        player.addMessage(new TranslatableText(String.format("Current rates on this seed: Blaze rates are %d/%d, Endermen "
                        + "rates are %d/%d, eye breaks are %d/%d",
                total_blazerods, total_blazes, total_pearls, total_endermen, broken_eyes, total_eyes)));
        world.playSound(player.x, player.y, player.z, "ambient.weather.thunder", 10000.0F, 0.8F + 0.2F);
    }



    public long getEnderEyeSeed() {
        return this.enderEyeSeed;
    }

    public long getEnderPearlSeed() {
        return this.enderPearlSeed;
    }

    public long getBlazeRodSeed() {
        return this.blazeRodSeed;
    }

    public long getFeatherSeed() {
        return this.featherSeed;
    }

    public void setEnderEyeSeed(long seed) {
        this.enderEyeSeed = seed;
    }

    public void setEnderPearlSeed(long seed) {
        this.enderPearlSeed = seed;
    }

    public void setBlazeRodSeed(long seed) {
        this.blazeRodSeed = seed;
    }

    public void setFeatherSeed(long seed) {
        this.featherSeed = seed;
    }
}
