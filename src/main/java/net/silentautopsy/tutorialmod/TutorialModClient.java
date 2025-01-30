package net.silentautopsy.tutorialmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.silentautopsy.tutorialmod.block.ModBlocks;

public class TutorialModClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        // This will make the transparecy parts of the blocks render correctly
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUBY_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUBY_TRAPDOOR, RenderLayer.getCutout());
    }
}
