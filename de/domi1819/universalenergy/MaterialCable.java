package de.domi1819.universalenergy;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLogic;

public class MaterialCable extends MaterialLogic
{
    public MaterialCable()
    {
        super(MapColor.ironColor);
    }
    
    public int getMaterialMobility()
    {
        return 2;
    }
    
    public boolean blocksMovement()
    {
        return true;
    }
}
