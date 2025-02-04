package net.silentautopsy.tutorialmod;

import net.fabricmc.api.ModInitializer;

import net.silentautopsy.tutorialmod.block.ModBlocks;
import net.silentautopsy.tutorialmod.item.ModFuels;
import net.silentautopsy.tutorialmod.item.ModItemGroups;
import net.silentautopsy.tutorialmod.item.ModItems;
import net.silentautopsy.tutorialmod.sound.ModSounds;
import net.silentautopsy.tutorialmod.util.ModCustomTrades;
import net.silentautopsy.tutorialmod.util.ModLootTableModifiers;
import net.silentautopsy.tutorialmod.villager.ModVillagers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TutorialMod implements ModInitializer
{
	public static final String MOD_ID = "tutorialmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize()
	{
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModFuels.registerFuels();
		ModLootTableModifiers.modifyLootTables();
		ModCustomTrades.registerCustomTrades();
		ModVillagers.registerVillagers();
		ModSounds.registerSounds();
	}
}