package net.silentautopsy.tutorialmod.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.silentautopsy.tutorialmod.item.ModArmorMaterials;
import org.spongepowered.include.com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Objects;

public class ModArmorItem extends ArmorItem
{
    public ModArmorItem(ArmorMaterial material, Type type, Settings settings)
    {
        super(material, type, settings);
    }

    //
    private static final Map<ArmorMaterial, StatusEffectInstance> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<ArmorMaterial, StatusEffectInstance>())
                    .put(ModArmorMaterials.RUBY, new StatusEffectInstance(StatusEffects.HASTE, 400, 1, false, false, true)).build();

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected)
    {
        if(!world.isClient())
            if(entity instanceof PlayerEntity player && hasFullSuitOfArmorOn(player))
                evaluateArmorEffects(player);

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    // Check if the player has a full suit of armor on
    private boolean hasFullSuitOfArmorOn(PlayerEntity player)
    {
        // Get the armor slots from the player's inventory
        ItemStack[] equipment = {
                player.getInventory().getArmorStack(0),
                player.getInventory().getArmorStack(1),
                player.getInventory().getArmorStack(2),
                player.getInventory().getArmorStack(3)
        };

        return !equipment[0].isEmpty() && !equipment[1].isEmpty() && !equipment[2].isEmpty() && !equipment[3].isEmpty();
    }

    private void evaluateArmorEffects(PlayerEntity player)
    {
        for(Map.Entry<ArmorMaterial, StatusEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet())
        {
            ArmorMaterial mapArmorMaterial = entry.getKey();
            StatusEffectInstance mapStatusEffect = entry.getValue();

            if(hasCorrectArmorOn(mapArmorMaterial, player))
                addStatusEffectForMaterial(player, mapStatusEffect);
        }
    }

    private boolean hasCorrectArmorOn(ArmorMaterial material, PlayerEntity player)
    {
        // Check if the player has a ArmorItem in each slot
        for(ItemStack armorStack : player.getInventory().armor)
        {
            if (!((armorStack).getItem() instanceof ArmorItem))
                return false;

        }

        ArmorItem[] equipment = {
                ((ArmorItem) player.getInventory().getArmorStack(0).getItem()),
                ((ArmorItem) player.getInventory().getArmorStack(1).getItem()),
                ((ArmorItem) player.getInventory().getArmorStack(2).getItem()),
                ((ArmorItem) player.getInventory().getArmorStack(3).getItem())
        };

        return equipment[0].getMaterial() == material &&
                equipment[1].getMaterial() == material &&
                equipment[2].getMaterial() == material &&
                equipment[3].getMaterial() == material;
    }

    private void addStatusEffectForMaterial(PlayerEntity player, StatusEffectInstance statusEffect)
    {
        try
        {
            boolean hasPlayerEffect = Objects.requireNonNull(player.getStatusEffect(statusEffect.getEffectType())).getDuration() > 1;

            if(!hasPlayerEffect)
                player.addStatusEffect(new StatusEffectInstance(statusEffect));
        }
        catch (NullPointerException e)
        {
            player.addStatusEffect(new StatusEffectInstance(statusEffect));
        }
    }
}
