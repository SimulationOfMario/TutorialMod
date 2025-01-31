package net.silentautopsy.tutorialmod.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import net.silentautopsy.tutorialmod.TutorialMod;
import net.silentautopsy.tutorialmod.item.ModItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModLootTableModifiers
{
    private static final Identifier JUNGLE_TEMPLE_ID = new Identifier("minecraft", "chests/jungle_temple");
    private static final Identifier CREEPER_ID = new Identifier("minecraft", "entities/creeper");
    private static final Identifier SUSPICIOUS_SAND_DESERT_PYRAMID_ID = new Identifier("minecraft", "archaeology/desert_pyramid");

    public static void modifyLootTables()
    {
        TutorialMod.LOGGER.info("Modifying Loot Tables for " + TutorialMod.MOD_ID);

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {

            if(JUNGLE_TEMPLE_ID.equals(id))
            {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f)) // Drops 100% of the time
                        .with(ItemEntry.builder(ModItems.METAL_DETECTOR)) // Adds the metal detector to the loot table
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)) // Sets the max and min count of the metal detector to 1
                        .build());

                tableBuilder.pool(poolBuilder.build());
            }

            if(CREEPER_ID.equals(id))
            {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f)) // Drops 100% of the time
                        .with(ItemEntry.builder(ModItems.COAL_BRIQUETTE)) // Adds the coal briquette to the loot table
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f))
                                .build());

                tableBuilder.pool(poolBuilder.build());
            }
        });

        LootTableEvents.REPLACE.register(((resourceManager, lootManager, id, original, source) -> {

            if(SUSPICIOUS_SAND_DESERT_PYRAMID_ID.equals(id))
            {
                List<LootPoolEntry> entries = new ArrayList<>(Arrays.asList(original.pools[0].entries));

                entries.add(ItemEntry.builder(ModItems.METAL_DETECTOR).build());
                entries.add(ItemEntry.builder(ModItems.COAL_BRIQUETTE).build());
                entries.add(ItemEntry.builder(ModItems.RUBY).build());

                LootPool.Builder pool = LootPool.builder().with(entries);
                return LootTable.builder().pool(pool).build();
            }

            return null;
        }));

    }
}
