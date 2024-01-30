package de.wandoria.playerinfo.inv;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class EnderchestView implements InventoryHolder {

    private Inventory inventory;

    public EnderchestView() {
        this.inventory = Bukkit.createInventory(this, 3 * 9, Component.text("Enderchest"));
    }

    public EnderchestView(Inventory toCopy) {
        this.inventory = Bukkit.createInventory(this, 3 * 9);
        inventory.setContents(toCopy.getContents());
    }

    @Override
    public @NotNull Inventory getInventory() {

        return inventory;
    }
}
