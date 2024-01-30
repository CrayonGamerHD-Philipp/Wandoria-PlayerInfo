package de.wandoria.playerinfo.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

    ItemStack is;

    public ItemBuilder(Material m, int amount){
        is = new ItemStack(m, amount);
    }

    public ItemBuilder setDisplayName(Component displayName){
        ItemMeta im = is.getItemMeta();
        im.displayName(displayName);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setCustomModelData(int customModelData){
        ItemMeta im = is.getItemMeta();
        im.setCustomModelData(customModelData);
        is.setItemMeta(im);
        return this;
    }

    public ItemStack toItemStack(){
        return is;
    }









}
