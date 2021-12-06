package racemod;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.ArrayList;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.world.BlockEvent;

public class LivingDropsEventHandler
{
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void onMobDrops(LivingDropsEvent event) {
    EntityLivingBase entityKilled = (EntityLivingBase)event.entity;

    if (entityKilled instanceof net.minecraft.entity.monster.EntityEnderman) {
      
      ExampleWorldSavedData seedInfo = ExampleWorldSavedData.get((World)DimensionManager.getWorld(0));
      
      event.drops.clear();
      
      int seedResult = Math.abs((int)seedInfo.updatePearlSeed()) % (int)Math.pow(16.0D, 4.0D);
      int numRolls = 1 + event.lootingLevel;
      int numDrops = 0;
      int i;
      for (i = 0; i < numRolls; i++) {
        
        boolean didPass = (seedResult % 16 < 10);
        
        if (didPass)
        {
          numDrops++;
        }
        
        seedResult /= 16;
      } 


      
      for (i = 0; i < numDrops; i++) {
        
        ItemStack item = new ItemStack(GameRegistry.findItem("minecraft", "ender_pearl"), 1, 0);
        event.drops.add(new EntityItem(event.entityLiving.worldObj, entityKilled.posX, entityKilled.posY, entityKilled.posZ, item));
      } 
    } 

    
    if (entityKilled instanceof net.minecraft.entity.passive.EntityChicken) {
      
      ExampleWorldSavedData seedInfo = ExampleWorldSavedData.get((World)DimensionManager.getWorld(0));
      
      ArrayList<Integer> toRemove = new ArrayList<Integer>();
      
      for (int i = 0; i < event.drops.size(); i++) {
        
        if (((EntityItem)event.drops.get(i)).getEntityItem().getDisplayName().equals("Feather")) {
          
          event.drops.remove(i);
          i--;
        } 
      } 

      
      int seedResult = Math.abs((int)seedInfo.updateFeatherSeed()) % (int)Math.pow(16.0D, 5.0D);
      int numRolls = 2 + event.lootingLevel;
      int numDrops = 0;
      int j;
      for (j = 0; j < numRolls; j++) {
        
        boolean didPass = (seedResult % 16 < 8);
        
        if (didPass)
        {
          numDrops++;
        }
        
        seedResult /= 16;
      } 


      
      for (j = 0; j < numDrops; j++) {
        
        ItemStack item = new ItemStack(GameRegistry.findItem("minecraft", "feather"), 1, 0);
        event.drops.add(new EntityItem(event.entityLiving.worldObj, entityKilled.posX, entityKilled.posY, entityKilled.posZ, item));
      } 
    } 





    
    if (entityKilled instanceof net.minecraft.entity.monster.EntityBlaze) {
      
      ExampleWorldSavedData seedInfo = ExampleWorldSavedData.get((World)DimensionManager.getWorld(0));
      
      event.drops.clear();
      
      int seedResult = Math.abs((int)seedInfo.updateRodSeed()) % (int)Math.pow(16.0D, 4.0D);
      int numRolls = 1 + event.lootingLevel;
      int numDrops = 0;
      int i;
      for (i = 0; i < numRolls; i++) {
        
        boolean didPass = (seedResult % 16 < 8);
        
        if (didPass)
        {
          numDrops++;
        }
        
        seedResult /= 16;
      } 


      
      for (i = 0; i < numDrops; i++) {
        
        ItemStack item = new ItemStack(GameRegistry.findItem("minecraft", "blaze_rod"), 1, 0);
        event.drops.add(new EntityItem(event.entityLiving.worldObj, entityKilled.posX, entityKilled.posY, entityKilled.posZ, item));
      } 
    } 
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void onHarvestDrops(BlockEvent.HarvestDropsEvent event) {
    if (event != null) {
      
      EntityPlayer harvester = event.harvester;
      
      if (harvester != null) {
        
        ItemStack item = harvester.inventory.getCurrentItem();
        
        if (item != null)
        {
          if (item.getDisplayName().equals("Shears"))
          {
            if (event.block != null)
            {
              if (event.block.getMaterial() == Material.leaves) {
                
                System.out.println(event.drops);
                
                event.drops.clear();
              } 
            }
          }
        }
      } 
    } 
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void setAttackTarget(LivingSetAttackTargetEvent event) {
    if (event.target instanceof EntityCreeper)
    {
      if (event.entity instanceof net.minecraft.entity.monster.EntitySkeleton || event.entity instanceof net.minecraft.entity.monster.EntityWitch)
      {
        ((EntityCreeper)event.target).setAttackTarget(null);;
      }
    }
    if (event.entity instanceof EntityCreeper)
    {
      if (event.target != null)
      {
        if (event.target instanceof net.minecraft.entity.monster.EntitySkeleton || event.target instanceof net.minecraft.entity.monster.EntityWitch)
        {
          ((EntityCreeper)event.entity).setAttackTarget(null);;
        }
      }
    }
  }
}