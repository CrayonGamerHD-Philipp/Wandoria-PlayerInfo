package de.wandoria.playerinfo.utils.inv;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface IndependantInventory {

    ItemStack getHelmet();
    ItemStack getChestplate();

    ItemStack getLeggings();

    ItemStack getBoots();

    ItemStack getOffhand();

    Inventory getInventory();


}
