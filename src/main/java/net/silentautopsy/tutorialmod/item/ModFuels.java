package net.silentautopsy.tutorialmod.item;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.silentautopsy.tutorialmod.TutorialMod;

public class ModFuels
{
    public static void registerFuels()
    {
        TutorialMod.LOGGER.info("Registering Mod Fuels for " + TutorialMod.MOD_ID);

        FuelRegistry.INSTANCE.add(ModItems.COAL_BRIQUETTE, 200);
    }
}
