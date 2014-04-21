package de.domi1819.universalenergy;

import java.util.ArrayList;
import java.util.List;

import de.domi1819.universalenergy.Cuboid.Point3D;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCable extends Block
{
    public static final float p = 1F / 16F, p6 = 6 * p, p10 = 10 * p;
    public static Icon[] textures = new Icon[16];
    public static final Cuboid center = new Cuboid(p6, p6, p6, p10, p10, p10), positiveX = new Cuboid(p10, p6, p6, 1, p10, p10), negativeX = new Cuboid(0, p6, p6, p6, p10, p10), positiveY = new Cuboid(p6, p10, p6, p10, 1, p10), negativeY = new Cuboid(p6, 0, p6, p10, p6, p10), positiveZ = new Cuboid(p6, p6, p10, p10, p10, 1), negativeZ = new Cuboid(p6, p6, 0, p10, p10, p6);
    
    public BlockCable(int blockID)
    {
        super(blockID, UniversalEnergy.materialCable);
        setBlockBounds(p6, p6, p6, p10, p10, p10);
    }
    
    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB overlay, List list, Entity entity)
    {
        addBB(x, y, z, center, overlay, list);
        if (UniversalEnergy.canConnect(world, x, y - 1, z)) addBB(x, y, z, negativeY, overlay, list);
        if (UniversalEnergy.canConnect(world, x, y + 1, z)) addBB(x, y, z, positiveY, overlay, list);
        if (UniversalEnergy.canConnect(world, x, y, z - 1)) addBB(x, y, z, negativeZ, overlay, list);
        if (UniversalEnergy.canConnect(world, x, y, z + 1)) addBB(x, y, z, positiveZ, overlay, list);
        if (UniversalEnergy.canConnect(world, x - 1, y, z)) addBB(x, y, z, negativeX, overlay, list);
        if (UniversalEnergy.canConnect(world, x + 1, y, z)) addBB(x, y, z, positiveX, overlay, list);
    }
    
    public static void addBB(int x, int y, int z, Cuboid cuboid, AxisAlignedBB overlay, List list)
    {
        AxisAlignedBB temp = AxisAlignedBB.getAABBPool().getAABB(x + cuboid.minX, y + cuboid.minY, z + cuboid.minZ, x + cuboid.maxX, y + cuboid.maxY, z + cuboid.maxZ);
        
        if (overlay.intersectsWith(temp)) list.add(temp);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) 
    {
        boolean[] canConnect = new boolean[6];
        
        canConnect[0] = UniversalEnergy.canConnect(world, x, y - 1, z);
        canConnect[1] = UniversalEnergy.canConnect(world, x, y + 1, z);
        canConnect[2] = UniversalEnergy.canConnect(world, x, y, z - 1);
        canConnect[3] = UniversalEnergy.canConnect(world, x, y, z + 1);
        canConnect[4] = UniversalEnergy.canConnect(world, x - 1, y, z);
        canConnect[5] = UniversalEnergy.canConnect(world, x + 1, y, z);
        
        float minX, minY, minZ;
        float maxX, maxY, maxZ;
        
        minX = minY = minZ = p6;
        maxX = maxY = maxZ = p10;

        if (canConnect[0])
            minY = 0F;
        if (canConnect[1])
            maxY = 1F;
        if (canConnect[2])
            minZ = 0F;
        if (canConnect[3])
            maxZ = 1F;
        if (canConnect[4])
            minX = 0F;
        if (canConnect[5])
            maxX = 1F;
        
        return AxisAlignedBB.getAABBPool().getAABB(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        boolean[] canConnect = new boolean[6];
        
        canConnect[0] = UniversalEnergy.canConnect(world, x, y - 1, z);
        canConnect[1] = UniversalEnergy.canConnect(world, x, y + 1, z);
        canConnect[2] = UniversalEnergy.canConnect(world, x, y, z - 1);
        canConnect[3] = UniversalEnergy.canConnect(world, x, y, z + 1);
        canConnect[4] = UniversalEnergy.canConnect(world, x - 1, y, z);
        canConnect[5] = UniversalEnergy.canConnect(world, x + 1, y, z);
        
        float minX, minY, minZ;
        float maxX, maxY, maxZ;
        
        minX = minY = minZ = p6;
        maxX = maxY = maxZ = p10;

        if (canConnect[0])
            minY = 0F;
        if (canConnect[1])
            maxY = 1F;
        if (canConnect[2])
            minZ = 0F;
        if (canConnect[3])
            maxZ = 1F;
        if (canConnect[4])
            minX = 0F;
        if (canConnect[5])
            maxX = 1F;
        
        setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 start, Vec3 end)
    {
        ArrayList<Cuboid> cuboids = new ArrayList<Cuboid>();
        
        cuboids.add(center);
        if (UniversalEnergy.canConnect(world, x, y - 1, z)) cuboids.add(negativeY);
        if (UniversalEnergy.canConnect(world, x, y + 1, z)) cuboids.add(positiveY);
        if (UniversalEnergy.canConnect(world, x, y, z - 1)) cuboids.add(negativeZ);
        if (UniversalEnergy.canConnect(world, x, y, z + 1)) cuboids.add(positiveZ);
        if (UniversalEnergy.canConnect(world, x - 1, y, z)) cuboids.add(negativeX);
        if (UniversalEnergy.canConnect(world, x + 1, y, z)) cuboids.add(positiveX);
        
        Cuboid[] sortedCuboids = Cuboid.sortAscending(cuboids.toArray(new Cuboid[cuboids.size()]), new Point3D(start.xCoord - x, start.yCoord - y, start.zCoord - z));
        
        MovingObjectPosition pos = null;
        for (int i = 0; i < sortedCuboids.length; i++)
        {
            pos = Raytracer.rayTrace(world, x, y, z, start, end, sortedCuboids[i]);
            if (pos != null) break;
        }
        
        return pos;
    }
    
    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        for (int i = 0; i < 16; i++)
            textures[i] = iconRegister.registerIcon("unienergy:cable" + i);
    }
    
    @Override
    public Icon getIcon(int side, int meta)
    {
        return textures[5];
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public int getRenderType()
    {
        return CableRenderer.renderID;
    }
}
