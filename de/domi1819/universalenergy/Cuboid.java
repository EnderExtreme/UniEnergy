package de.domi1819.universalenergy;

import java.util.ArrayList;

public class Cuboid
{
    public double minX, minY, minZ, maxX, maxY, maxZ;
    
    public Cuboid(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
    {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }
    
    public Cuboid(Point3D min, Point3D max)
    {
        this(min.x, min.y, min.z, max.x, max.y, max.z);
    }
    
    public Cuboid copy()
    {
        return new Cuboid(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    public Cuboid shift(double x, double y, double z)
    {
        minX += x;
        maxX += x;
        minY += y;
        maxY += y;
        minZ += z;
        maxZ += z;
        
        return this;
    }
    
    public Point3D getCenter()
    {
        return new Point3D((minX + maxX) / 2, (minY + maxY) / 2, (minZ + maxZ) / 2);
    }
    
    public double centerDistanceTo(Point3D point)
    {
        return getCenter().distanceTo(point);
    }
    
    public Point3D[] getEdges()
    {
        return new Point3D[] { new Point3D(minX, minY, minZ), new Point3D(maxX, minY, minZ), new Point3D(minX, maxY, minZ), new Point3D(minX, minY, maxZ), new Point3D(maxX, maxY, minZ), new Point3D(maxX, minY, maxZ), new Point3D(minX, maxY, maxZ), new Point3D(maxX, maxY, maxZ) }; 
    }
    
    public Point3D getClosestEdgeTo(Point3D point)
    {
        int nearest = 0;
        double nearestValue = Double.MAX_VALUE;
        Point3D[] edges = getEdges();
        
        for (int i = 0; i < edges.length; i++)
        {
            double dist = point.distanceTo(edges[i]);
            if (dist < nearestValue)
            {
                nearestValue = dist;
                nearest = i;
            }
        }
        
        return edges[nearest];
    }
    
    public double closestEdgeDistanceTo(Point3D point)
    {
        return getClosestEdgeTo(point).distanceTo(point);
    }
    
    /**
     *  Warning: Do NOT use when the cuboids are intersecting!
     */
    public static Cuboid[] sortAscending(Cuboid[] cuboids, Point3D point)
    {
        ArrayList<Cuboid> cuboidsSorted = new ArrayList<Cuboid>();
        double[] distances = new double[cuboids.length];
        cuboidsSorted.add(cuboids[0]);
        
        for(int i = 1; i < cuboids.length; i++)
        {
            double distance = cuboids[i].closestEdgeDistanceTo(point);
            int j;
            for(j = 0; j < cuboidsSorted.size(); j++)
            {
                if (cuboidsSorted.get(j).closestEdgeDistanceTo(point) > distance)
                    break;
            }
            cuboidsSorted.add(j, cuboids[i]);
        }
        
        return cuboidsSorted.toArray(new Cuboid[cuboidsSorted.size()]);
    }
    
    public static class Point3D
    {
        public double x, y, z;
        
        public Point3D(double x, double y, double z)
        {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public double distanceTo(Point3D other)
        {
            return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math.pow(z - other.z, 2));
        }
    }
}
