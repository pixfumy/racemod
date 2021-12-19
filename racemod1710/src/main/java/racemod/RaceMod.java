 package racemod;
 
 import cpw.mods.fml.common.Mod;
 import cpw.mods.fml.common.Mod.EventHandler;
 import cpw.mods.fml.common.event.FMLInitializationEvent;
 import net.minecraftforge.common.MinecraftForge;

 @Mod(modid = "racemod", name = "Race Mod", version = "0.4.0")
 public class RaceMod {
   @EventHandler
   public void init(FMLInitializationEvent event) {
       MinecraftForge.EVENT_BUS.register(new LivingDropsEventHandler());
	   MinecraftForge.EVENT_BUS.register(new EnderEyeBreakEventHandler());
	   MinecraftForge.EVENT_BUS.register(new BedrockEventHandler());
   }
}