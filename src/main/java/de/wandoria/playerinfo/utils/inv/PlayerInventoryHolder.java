package de.wandoria.playerinfo.utils.inv;

import lombok.Getter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@Getter
public class PlayerInventoryHolder implements IndependantInventory{

    private final PlayerInventory playerInventory;
    public PlayerInventoryHolder(PlayerInventory playerInventory) {
        this.playerInventory = playerInventory;
    }

    @Override
    public ItemStack getHelmet() {
        return playerInventory.getHelmet();
    }

    @Override
    public ItemStack getChestplate() {
        return playerInventory.getChestplate();
    }

    @Override
    public ItemStack getLeggings() {
        return playerInventory.getLeggings();
    }

    @Override
    public ItemStack getBoots() {
        return playerInventory.getBoots();
    }

    @Override
    public ItemStack getOffhand() {
        return playerInventory.getItemInOffHand();
    }

    @Override
    public Inventory getInventory() {
        return playerInventory;
    }
}
