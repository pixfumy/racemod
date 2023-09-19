package net.pixfumy.racemod;

import net.minecraft.entity.player.ClientPlayerEntity;
import net.pixfumy.racemod.mixinterface.ILevelProperties;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

import java.util.HashMap;

public class SeedfinderUtil {
    public static void tellPlayerCurrentRates(World world) {
        RNGStreamGenerator main = ((ILevelProperties)world.getLevelProperties()).getRngStreamGenerator();
        RNGStreamGenerator dummy = new RNGStreamGenerator();
        dummy.rngSeeds = new HashMap() {
            {
                put("blazeRodSeed", main.getSeed("blazeRodSeed"));
                put("enderPearlSeed", main.getSeed("enderPearlSeed"));
                put("featherSeed", main.getSeed("featherSeed"));
                put("enderEyeSeed", main.getSeed("enderEyeSeed"));
                put("flintSeed", main.getSeed("flintSeed"));
                put("stringSeed", main.getSeed("stringSeed"));
            }
        };
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
            int seedResult = (int) dummy.getAndUpdateSeed("blazeRodSeed");
            boolean didPass = (seedResult % 16 < 8);
            if (didPass) {
                total_blazerods++;
            }
            total_blazes++;
        }
        while (total_pearls < 14) {
            int seedResult = (int) dummy.getAndUpdateSeed("enderPearlSeed");
            boolean didPass = (seedResult % 16 < 10);
            if (didPass) {
                total_pearls++;
            }
            total_endermen++;
        }
        while (total_eyes < 5) {
            int seedResult = (int)dummy.getAndUpdateSeed("enderEyeSeed");
            boolean didPass = (seedResult % 5 > 0);
            if (!didPass) {
                broken_eyes++;
            }
            total_eyes++;
        }
        while (total_feathers < 6) {
            int seedResult = (int) dummy.getAndUpdateSeed("featherSeed");
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
            int seedResult = (int) dummy.getAndUpdateSeed("flintSeed");
            if (seedResult % 10 == 0) {
                total_flint++;
            }
            total_gravel++;
        }
        while (total_string < 3) {
            int seedResult = (int) dummy.getAndUpdateSeed("stringSeed");
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
        player.addMessage(new TranslatableText(""));
        player.addMessage(new TranslatableText(String.format("Current rates on this seed: Blaze rates are %d/%d, Endermen "
                        + "rates are %d/%d, eye breaks are %d/%d, feather rates are %d/%d, flint rates are %d/%d, string rates are %d/%d",
                total_blazerods, total_blazes, total_pearls, total_endermen, broken_eyes, total_eyes, total_feathers, total_chickens, total_flint, total_gravel,
                total_string, total_spiders
        )));
    }
}
