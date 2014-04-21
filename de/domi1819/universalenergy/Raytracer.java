package de.domi1819.universalenergy;

import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Raytracer
{
    /**
     *  Important: cuboid needs to be absolute (within [0|0|0] and [1|1|1])
     */
    public static MovingObjectPosition rayTrace(World world, int x, int y, int z, Vec3 start, Vec3 end, Cuboid cuboid)
    {
        start = start.addVector((double) (-x), (double) (-y), (double) (-z));
        end = end.addVector((double) (-x), (double) (-y), (double) (-z));
        
        Vec3 vec32 = start.getIntermediateWithXValue(end, cuboid.minX);
        Vec3 vec33 = start.getIntermediateWithXValue(end, cuboid.maxX);
        Vec3 vec34 = start.getIntermediateWithYValue(end, cuboid.minY);
        Vec3 vec35 = start.getIntermediateWithYValue(end, cuboid.maxY);
        Vec3 vec36 = start.getIntermediateWithZValue(end, cuboid.minZ);
        Vec3 vec37 = start.getIntermediateWithZValue(end, cuboid.maxZ);
        
        if (!isVecInsideYZBounds(vec32, cuboid))
        {
            vec32 = null;
        }
        
        if (!isVecInsideYZBounds(vec33, cuboid))
        {
            vec33 = null;
        }
        
        if (!isVecInsideXZBounds(vec34, cuboid))
        {
            vec34 = null;
        }
        
        if (!isVecInsideXZBounds(vec35, cuboid))
        {
            vec35 = null;
        }
        
        if (!isVecInsideXYBounds(vec36, cuboid))
        {
            vec36 = null;
        }
        
        if (!isVecInsideXYBounds(vec37, cuboid))
        {
            vec37 = null;
        }
        
        Vec3 vec38 = null;
        
        if (vec32 != null && (vec38 == null || start.squareDistanceTo(vec32) < start.squareDistanceTo(vec38)))
        {
            vec38 = vec32;
        }
        
        if (vec33 != null && (vec38 == null || start.squareDistanceTo(vec33) < start.squareDistanceTo(vec38)))
        {
            vec38 = vec33;
        }
        
        if (vec34 != null && (vec38 == null || start.squareDistanceTo(vec34) < start.squareDistanceTo(vec38)))
        {
            vec38 = vec34;
        }
        
        if (vec35 != null && (vec38 == null || start.squareDistanceTo(vec35) < start.squareDistanceTo(vec38)))
        {
            vec38 = vec35;
        }
        
        if (vec36 != null && (vec38 == null || start.squareDistanceTo(vec36) < start.squareDistanceTo(vec38)))
        {
            vec38 = vec36;
        }
        
        if (vec37 != null && (vec38 == null || start.squareDistanceTo(vec37) < start.squareDistanceTo(vec38)))
        {
            vec38 = vec37;
        }
        
        if (vec38 == null)
        {
            return null;
        }
        else
        {
            byte b0 = -1;
            
            if (vec38 == vec32)
            {
                b0 = 4;
            }
            
            if (vec38 == vec33)
            {
                b0 = 5;
            }
            
            if (vec38 == vec34)
            {
                b0 = 0;
            }
            
            if (vec38 == vec35)
            {
                b0 = 1;
            }
            
            if (vec38 == vec36)
            {
                b0 = 2;
            }
            
            if (vec38 == vec37)
            {
                b0 = 3;
            }
            
            return new MovingObjectPosition(x, y, z, b0, vec38.addVector((double) x, (double) y, (double) z));
        }
    }
    
    private static boolean isVecInsideYZBounds(Vec3 vec, Cuboid cuboid)
    {
        return vec == null ? false : vec.yCoord >= cuboid.minY && vec.yCoord <= cuboid.maxY && vec.zCoord >= cuboid.minZ && vec.zCoord <= cuboid.maxZ;
    }
    
    private static boolean isVecInsideXZBounds(Vec3 vec, Cuboid cuboid)
    {
        return vec == null ? false : vec.xCoord >= cuboid.minX && vec.xCoord <= cuboid.maxX && vec.zCoord >= cuboid.minZ && vec.zCoord <= cuboid.maxZ;
    }
    
    private static boolean isVecInsideXYBounds(Vec3 vec, Cuboid cuboid)
    {
        return vec == null ? false : vec.xCoord >= cuboid.minX && vec.xCoord <= cuboid.maxX && vec.yCoord >= cuboid.minY && vec.yCoord <= cuboid.maxY;
    }
}
