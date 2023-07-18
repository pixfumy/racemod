package io.github.marinersfan824.racemod.mixin.command;

import io.github.marinersfan824.racemod.RNGStreamGenerator;
import io.github.marinersfan824.racemod.SeedfinderUtil;
import io.github.marinersfan824.racemod.mixinterface.ILevelProperties;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.AbstractCommand;
import net.minecraft.command.CommandSource;
import net.minecraft.command.IncorrectUsageException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.CommandRegistry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Mixin(CommandManager.class)
public class CommandManagerMixin extends CommandRegistry {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void addRatesCommand(CallbackInfo ci) {
        this.registerCommand(new AbstractCommand() {
            @Override
            public int getPermissionLevel() {
                return 2;
            }

            @Override
            public String getCommandName() {
                return "rates";
            }

            @Override
            public String getUsageTranslationKey(CommandSource source) {
                return "/rates";
            }

            @Override
            public void execute(CommandSource source, String[] args) {
                if (args.length == 0) {
                    World overWorld = ((ServerWorld)source.getWorld()).getServer().getWorld();
                    SeedfinderUtil.tellPlayerCurrentRates(overWorld); // ensure that the world used is the overworld
                } else {
                    throw new IncorrectUsageException("Usage: /rates", new Object[0]);
                }
            }

            @Override
            public int compareTo(@NotNull Object o) {
                return 0;
            }
        });

        this.registerCommand(new AbstractCommand() {
            @Override
            public int getPermissionLevel() {
                return 2;
            }

            @Override
            public String getCommandName() {
                return "resetRNGSeed";
            }

            @Override
            public String getUsageTranslationKey(CommandSource source) {
                return "resetRNGSeed";
            }

            @Override
            public void execute(CommandSource source, String[] args) {
                if (args.length == 1) {
                    World overWorld = ((ServerWorld)source.getWorld()).getServer().getWorld();
                    RNGStreamGenerator rngStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRngStreamGenerator();
                    String seedType = args[0];
                    if (!rngStreamGenerator.keySet().contains(seedType)) {
                        throw new IncorrectUsageException("An RNG seed with name " + seedType + " does not exist.", new Object[0]);
                    }
                    rngStreamGenerator.setSeed(seedType, (new Random()).nextLong());
                    MinecraftClient.getInstance().field_3805.sendMessage(new LiteralText(""));
                    MinecraftClient.getInstance().field_3805.sendMessage(new LiteralText("RNG seed " + seedType + " changed."));
                    SeedfinderUtil.tellPlayerCurrentRates(overWorld);
                } else {
                    throw new IncorrectUsageException("Usage: /resetRNGSeed <seedName>", new Object[0]);
                }
            }

            @Override
            public List method_3276(CommandSource source, String[] strings) {
                World overWorld = ((ServerWorld)source.getWorld()).getServer().getWorld();
                RNGStreamGenerator rngStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRngStreamGenerator();
                if (strings.length == 1) {
                    return method_2894(strings, rngStreamGenerator.keySet().toArray(new String[0]));
                }
                return null;
            }

            @Override
            public int compareTo(@NotNull Object o) {
                return 0;
            }
        });
    }
}
