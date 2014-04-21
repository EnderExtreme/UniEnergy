package de.domi1819.universalenergy;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "unienergy", name = "Universal Energy", version = "0.3.2")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class UniversalEnergy
{
    public static Block blockUniCable;
    public static Material materialCable = new MaterialCable();
    
    public static EnergyManager energyManager = new EnergyManager();
    
    @Instance("unienergy")
    public static UniversalEnergy instance;
    
    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        blockUniCable = new BlockCable(870).setHardness(2.5F).setResistance(2F).setUnlocalizedName("blockUniCable").setCreativeTab(CreativeTabs.tabRedstone).setLightOpacity(0);
        GameRegistry.registerBlock(blockUniCable, "blockUniCable");
        LanguageRegistry.addName(blockUniCable, "Universal Energy Cable");
        
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) RenderingRegistry.registerBlockHandler(new CableRenderer(RenderingRegistry.getNextAvailableRenderId()));
    
        MinecraftForge.EVENT_BUS.register(energyManager);
        TickRegistry.registerTickHandler(energyManager, Side.SERVER);
    }
    
    public static boolean canConnect(IBlockAccess world, int x, int y, int z)
    {
        return world.getBlockId(x, y, z) == blockUniCable.blockID;
    }
}
