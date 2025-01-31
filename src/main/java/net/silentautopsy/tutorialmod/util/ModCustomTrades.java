package net.silentautopsy.tutorialmod.util;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.silentautopsy.tutorialmod.TutorialMod;
import net.silentautopsy.tutorialmod.item.ModItems;

public class ModCustomTrades
{
    public static void registerCustomTrades()
    {
        TutorialMod.LOGGER.info("Registering Custom Villager Trades for " + TutorialMod.MOD_ID);

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> {

            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.TOMATO, 10),
                    6, 5, 0.05f)
            ));

            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ModItems.TOMATO_SEEDS, 30),
                    new ItemStack(Items.EMERALD, 2),
                    2, 7, 0.075f)
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 2, factories -> {

            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(ModItems.RUBY, 10),
                    new ItemStack(ModItems.CORN, 40),
                    3, 10, 0.09f)
            ));

        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, 1, factories -> {

            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ModItems.RUBY, 32),
                    new ItemStack(Items.BOOK, 1),
                    EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(Enchantments.PIERCING, 3)),
                    3, 10, 0.09f)
            ));

        });

        TradeOfferHelper.registerWanderingTraderOffers(2, factories -> {

            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ModItems.RAW_RUBY, 16),
                    new ItemStack(ModItems.METAL_DETECTOR, 1),
                    1, 12, 0.09f)
            ));

        });

    }
}
