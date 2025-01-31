package net.silentautopsy.tutorialmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.AnyOfLootCondition;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.silentautopsy.tutorialmod.block.ModBlocks;
import net.silentautopsy.tutorialmod.block.custom.CornCropBlock;
import net.silentautopsy.tutorialmod.block.custom.TomatoCropBlock;
import net.silentautopsy.tutorialmod.item.ModItems;

public class ModLootTableProvider extends FabricBlockLootTableProvider
{
    public ModLootTableProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput);
    }

    @Override
    public void generate()
    {
        addDrop(ModBlocks.RUBY_BLOCK);
        addDrop(ModBlocks.RAW_RUBY_BLOCK);
        addDrop(ModBlocks.SOUND_BLOCK);

        addDrop(ModBlocks.RUBY_ORE, rubyOreDrops(ModBlocks.RUBY_ORE, ModItems.RAW_RUBY, 2.0F, 5.0F));
        addDrop(ModBlocks.DEEPSLATE_RUBY_ORE, rubyOreDrops(ModBlocks.DEEPSLATE_RUBY_ORE, ModItems.RAW_RUBY, 3.0F, 6.0F));
        addDrop(ModBlocks.NETHER_RUBY_ORE, rubyOreDrops(ModBlocks.NETHER_RUBY_ORE, ModItems.RAW_RUBY, 2.0F, 5.0F));
        addDrop(ModBlocks.END_STONE_RUBY_ORE, rubyOreDrops(ModBlocks.END_STONE_RUBY_ORE, ModItems.RAW_RUBY, 4.0F, 8.0F));

        addDrop(ModBlocks.RUBY_STAIRS);
        addDrop(ModBlocks.RUBY_WALL);
        addDrop(ModBlocks.RUBY_FENCE);
        addDrop(ModBlocks.RUBY_FENCE_GATE);
        addDrop(ModBlocks.RUBY_BUTTON);
        addDrop(ModBlocks.RUBY_PRESSURE_PLATE);
        addDrop(ModBlocks.RUBY_TRAPDOOR);

        addDrop(ModBlocks.RUBY_DOOR, doorDrops(ModBlocks.RUBY_DOOR));
        addDrop(ModBlocks.RUBY_SLAB, slabDrops(ModBlocks.RUBY_SLAB));

        addDrop(ModBlocks.DAHLIA);
        addPottedPlantDrops(ModBlocks.POTTED_DAHLIA);

        BlockStatePropertyLootCondition.Builder builder = BlockStatePropertyLootCondition.builder(ModBlocks.TOMATO_CROP)
                .properties(StatePredicate.Builder.create()
                        .exactMatch(TomatoCropBlock.AGE, 5)
                );
        addDrop(ModBlocks.TOMATO_CROP, cropDrops(ModBlocks.TOMATO_CROP, ModItems.TOMATO, ModItems.TOMATO_SEEDS, builder));

        AnyOfLootCondition.Builder builder2 =
                BlockStatePropertyLootCondition.builder(ModBlocks.CORN_CROP)
                    .properties(StatePredicate.Builder.create()
                            .exactMatch(CornCropBlock.AGE, 7)
                    )
                .or(BlockStatePropertyLootCondition.builder(ModBlocks.CORN_CROP)
                    .properties(StatePredicate.Builder.create()
                            .exactMatch(CornCropBlock.AGE, 8)
                    )
                );
        addDrop(ModBlocks.CORN_CROP, cropDrops(ModBlocks.CORN_CROP, ModItems.CORN, ModItems.CORN_SEEDS, builder2));



    }

    public LootTable.Builder rubyOreDrops(Block drop, Item item, float min, float max)
    {
        return BlockLootTableGenerator.dropsWithSilkTouch(drop, (LootPoolEntry.Builder) this.applyExplosionDecay(drop,
                ((LeafEntry.Builder) ItemEntry.builder(item)
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(min, max)))
                    ).apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
                )
        );
    }

}
