package net.silentautopsy.tutorialmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.silentautopsy.tutorialmod.item.ModItems;

public class TomatoCropBlock extends CropBlock
{
    public static final int MAX_AGE = 5;
    public static final IntProperty AGE = Properties.AGE_5;
            // = IntProperty.of("age", 0, MAX_AGE);

    public TomatoCropBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem()
    {
        return ModItems.TOMATO_SEEDS;
    }

    @Override
    protected IntProperty getAgeProperty()
    {
        return AGE;
    }

    @Override
    public int getMaxAge()
    {
        return MAX_AGE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(AGE);
    }
}
