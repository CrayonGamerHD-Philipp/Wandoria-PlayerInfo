package de.wandoria.playerinfo.inv;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class SelectInventoryView implements InventoryHolder {

    private Inventory inventory;

    public SelectInventoryView() {
        this.inventory = Bukkit.createInventory(this, 3 * 9, "Wähle eine Kategorie aus!");
    }

    @Override
    public @NotNull Inventory getInventory() {

        return inventory;
    }

}
