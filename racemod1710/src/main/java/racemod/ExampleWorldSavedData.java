package racemod;

import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;



public class ExampleWorldSavedData extends WorldSavedData
{
  private static final String DATA_NAME = "msl-race-mod_ExampleData";
  public long pearlSeed;
  public long featherSeed;
  public long rodSeed;
	public long eyeSeed;
  
  public ExampleWorldSavedData() {
    super("msl-race-mod_ExampleData");
  }
  
  public ExampleWorldSavedData(String s) {
    super(s);
  }


  
  public void init(World world) {
    this.pearlSeed = world.getSeed() ^ 0x11101010L;
    this.featherSeed = world.getSeed() ^ 0x1111101101000010L;
    this.rodSeed = world.getSeed() ^ 0xF111B7010L;
	  this.eyeSeed = world.getSeed() ^ 0x99A2B75BBL;
    int i;
    for (i = 0; i < 17; i++)
    {
      updatePearlSeed();
    }
    
    for (i = 0; i < 43; i++)
    {
      updateFeatherSeed();
    }
    
    for (i = 0; i < 97; i++)
    {
      updateRodSeed();
    }
	  for (i = 0; i < 24; i++) {
		  updateEyeSeed();
	  }
  }

  
  public long updatePearlSeed() {
    long oldSeed = this.pearlSeed;
    
    Random random = new Random(oldSeed);
    this.pearlSeed = random.nextLong();
    
    markDirty();
    
    return oldSeed;
  }


  
  public long updateFeatherSeed() {
    long oldSeed = this.featherSeed;
    
    Random random = new Random(oldSeed);
    this.featherSeed = random.nextLong();
    
    markDirty();
    
    return oldSeed;
  }


  
  public long updateRodSeed() {
    long oldSeed = this.rodSeed;
    
    Random random = new Random(oldSeed);
    this.rodSeed = random.nextLong();
    
    markDirty();
    
    return oldSeed;
  }

	public long updateEyeSeed() {
		long oldSeed = this.eyeSeed;
		Random random = new Random(oldSeed);
		this.eyeSeed = random.nextLong();
		markDirty();
		return oldSeed;
	}


  
  public void readFromNBT(NBTTagCompound nbt) {
    System.out.println("WorldData readFromNBT");
    
    this.pearlSeed = nbt.getLong("pearlSeed");
    this.featherSeed = nbt.getLong("featherSeed");
    this.rodSeed = nbt.getLong("rodSeed");
    this.eyeSeed = nbt.getLong("eyeSeed");
  }

  public void writeToNBT(NBTTagCompound nbt) {
    nbt.setLong("pearlSeed", this.pearlSeed);
    nbt.setLong("featherSeed", this.featherSeed);
    nbt.setLong("rodSeed", this.rodSeed);
	  nbt.setLong("eyeSeed", this.eyeSeed);
  }

  
  public static ExampleWorldSavedData get(World world) {
    MapStorage storage = world.perWorldStorage;
    if (storage == null) {
      return null;
    }
    
    ExampleWorldSavedData result = null;

    try {
      result = (ExampleWorldSavedData)storage.loadData(ExampleWorldSavedData.class, "msl-race-mod_ExampleData");
    } catch (Exception exception) {

      
      try {
        result = (ExampleWorldSavedData)storage.getClass().getMethod("getOrLoadData", new Class[] { Class.class, String.class }).invoke(storage, new Object[] { WorldSavedData.class, "msl-race-mod_ExampleData" });
      }
      catch (Exception exception1) {}
    } 
    
    if (result == null) {
      result = new ExampleWorldSavedData("msl-race-mod_ExampleData");
      result.init(world);
      storage.setData("msl-race-mod_ExampleData", result);
    } 
    return result;
  }

}