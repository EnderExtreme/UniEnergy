package de.domi1819.universalenergy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class CableRenderer implements ISimpleBlockRenderingHandler
{
    public static int renderID;
    
    public static final float p = 1F / 16F, p6 = 6 * p, p10 = 10 * p;
    
    float minU, maxU, minV, maxV;
    Tessellator t;
    
    public CableRenderer(int renderID)
    {
        CableRenderer.renderID = renderID;
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        t = Tessellator.instance;
        
        boolean[] canConnect = new boolean[6];
        
        canConnect[0] = UniversalEnergy.canConnect(world, x, y - 1, z);
        canConnect[1] = UniversalEnergy.canConnect(world, x, y + 1, z);
        canConnect[2] = UniversalEnergy.canConnect(world, x, y, z - 1);
        canConnect[3] = UniversalEnergy.canConnect(world, x, y, z + 1);
        canConnect[4] = UniversalEnergy.canConnect(world, x - 1, y, z);
        canConnect[5] = UniversalEnergy.canConnect(world, x + 1, y, z);
                
        t.setBrightness(UniversalEnergy.blockUniCable.getMixedBrightnessForBlock(world, x, y, z));
        t.setColorOpaque_F(1F, 1F, 1F);
        
        bindTexture(canConnect[2], canConnect[0], canConnect[3], canConnect[1]);
        
        vertexUV(x, y, z, p6, 1, 0, minU, minV);
        vertexUV(x, y, z, p6, 0, 0, minU, maxV);
        vertexUV(x, y, z, p6, 0, 1, maxU, maxV);
        vertexUV(x, y, z, p6, 1, 1, maxU, minV);

        bindTexture(canConnect[4], canConnect[0], canConnect[5], canConnect[1]);
        
        vertexUV(x, y, z, 0, 1, p10, minU, minV);
        vertexUV(x, y, z, 0, 0, p10, minU, maxV);
        vertexUV(x, y, z, 1, 0, p10, maxU, maxV);
        vertexUV(x, y, z, 1, 1, p10, maxU, minV);

        bindTexture(canConnect[3], canConnect[0], canConnect[2], canConnect[1]);

        vertexUV(x, y, z, p10, 1, 1, minU, minV);
        vertexUV(x, y, z, p10, 0, 1, minU, maxV);
        vertexUV(x, y, z, p10, 0, 0, maxU, maxV);
        vertexUV(x, y, z, p10, 1, 0, maxU, minV);

        bindTexture(canConnect[5], canConnect[0], canConnect[4], canConnect[1]);
        
        vertexUV(x, y, z, 1, 1, p6, minU, minV);
        vertexUV(x, y, z, 1, 0, p6, minU, maxV);
        vertexUV(x, y, z, 0, 0, p6, maxU, maxV);
        vertexUV(x, y, z, 0, 1, p6, maxU, minV);

        bindTexture(canConnect[4], canConnect[3], canConnect[5], canConnect[2]);
        
        vertexUV(x, y, z, 0, p10, 0, minU, minV);
        vertexUV(x, y, z, 0, p10, 1, minU, maxV);
        vertexUV(x, y, z, 1, p10, 1, maxU, maxV);
        vertexUV(x, y, z, 1, p10, 0, maxU, minV);

        bindTexture(canConnect[2], canConnect[5], canConnect[3], canConnect[4]);
        
        vertexUV(x, y, z, 0, p6, 0, minU, minV);
        vertexUV(x, y, z, 1, p6, 0, minU, maxV);
        vertexUV(x, y, z, 1, p6, 1, maxU, maxV);
        vertexUV(x, y, z, 0, p6, 1, maxU, minV);
        
        return true;
    }
    
    public void vertexUV(int x, int y, int z, float renderX, float renderY, float renderZ, float u, float v)
    {
        t.addVertexWithUV(x + renderX, y + renderY, z + renderZ, u, v);
    }
    
    public void bindTexture(boolean connectMinU, boolean connectMaxV, boolean connectMaxU, boolean connectMinV)
    {
        int i = (connectMinU ? 1 : 0) + (connectMaxV ? 2 : 0) + (connectMaxU ? 4 : 0) + (connectMinV ? 8 : 0);

        if (i >= 0 && i < 16)
        {
            minU = BlockCable.textures[i].getMinU();
            maxU = BlockCable.textures[i].getMaxU();
            minV = BlockCable.textures[i].getMinV();
            maxV = BlockCable.textures[i].getMaxV();
        }
    }

    @Override
    public boolean shouldRender3DInInventory()
    {
        return false;
    }
    
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    { 
    }

    @Override
    public int getRenderId()
    {
        return renderID;
    }
}
