package de.domi1819.universalenergy;

import java.util.ArrayList;
import java.util.EnumSet;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;

public class EnergyManager implements ITickHandler
{
    public ArrayList<EnergyNetwork> networks = new ArrayList<EnergyNetwork>();
    
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
    }
    
    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        
    }
    
    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.WORLD);
    }
    
    @Override
    public String getLabel()
    {
        return "unienergy";
    }
    
    @ForgeSubscribe
    public void worldLoad(WorldEvent.Load event)
    {
        
    }
    
    @ForgeSubscribe
    public void worldSave(WorldEvent.Save event)
    {
        
    }
    
    public static class EnergyNetwork
    {
        public World world;
        public ArrayList<EnergyNetworkComponent> components = new ArrayList<EnergyNetworkComponent>();
        public long energyStored = 0;
        public long maxEnergyStored = 0;
        
        public EnergyNetwork(World world)
        {
            this.world = world;
        }
        
        public void add(EnergyNetworkComponent component)
        {
            components.add(component);
            maxEnergyStored = 20480 * components.size();
        }
        
        public EnergyNetwork merge(EnergyNetwork other)
        {
            for (int i = 0; i < other.components.size(); i++)
                components.add(other.components.get(i));
            
            return this;
        }
    }
    
    public static class EnergyNetworkComponent
    {
        public World world;
        public int x, y, z;
        
        public EnergyNetworkComponent(World world, int x, int y, int z)
        {
            this.world = world;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
