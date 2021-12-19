 package racemod;
 

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

 @Mod(modid = "racemod", name = "racemod", version = "0.4.1")
 public class RaceMod {
   @EventHandler
   public void init(FMLInitializationEvent event) {
       MinecraftForge.EVENT_BUS.register(new LivingDropsEventHandler());
	   MinecraftForge.EVENT_BUS.register(new EnderEyeBreakEventHandler());
	   MinecraftForge.EVENT_BUS.register(new BedrockEventHandler());
   }
}