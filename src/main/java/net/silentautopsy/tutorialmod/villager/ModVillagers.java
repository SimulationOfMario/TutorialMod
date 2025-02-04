package net.silentautopsy.tutorialmod.villager;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import net.silentautopsy.tutorialmod.TutorialMod;
import net.silentautopsy.tutorialmod.block.ModBlocks;

public class ModVillagers
{
    public static void registerVillagers()
    {
        TutorialMod.LOGGER.info("Registering Villagers " + TutorialMod.MOD_ID);
    }

    public static RegistryKey<PointOfInterestType> poiKey(String name)
    {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, new Identifier(TutorialMod.MOD_ID, name));
    }

    // Sets the work station for the villager
    private static PointOfInterestType registerPoi(String name, Block block)
    {
        return PointOfInterestHelper.register(new Identifier(TutorialMod.MOD_ID, name), 1, 1, block);
    }

    private static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type)
    {
        return Registry.register(Registries.VILLAGER_PROFESSION, new Identifier(TutorialMod.MOD_ID, name),
                new VillagerProfession(name, entry -> entry.matchesKey(type), entry -> entry.matchesKey(type),
                        ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_SHEPHERD
                )
        );
    }

    public static final RegistryKey<PointOfInterestType> SOUND_POI_KEY = poiKey("sound_poi");
    public static final PointOfInterestType SOUND_POI = registerPoi("sound_poi", ModBlocks.SOUND_BLOCK);
    public static final VillagerProfession SOUND_MASTER = registerProfession("sound_master", SOUND_POI_KEY);
}
