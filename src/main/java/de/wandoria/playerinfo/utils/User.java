package de.wandoria.playerinfo.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.william278.husksync.api.BukkitHuskSyncAPI;
import net.william278.husksync.api.HuskSyncAPI;
import net.william278.husksync.data.BukkitData;
import net.william278.husksync.data.Data;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Setter
@Getter
@AllArgsConstructor
public class User {

    private final HuskSyncAPI huskAPI = BukkitHuskSyncAPI.getInstance();
    UUID uuid;
    String banner;

    Inventory inventory; // https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/inventory/PlayerInventory.html
    Inventory enderchst;

    // Methods
    public void save() {
    }

    public void load() {
    }

    public ItemStack getHelmet( ) {
        return ((PlayerInventory) inventory).getHelmet();
    }

    public ItemStack getChestplate () {
        return ((PlayerInventory) inventory).getChestplate();
    }

    public ItemStack getLeggings () {
        return ((PlayerInventory) inventory).getLeggings();
    }

    public ItemStack getBoots () {
        return ((PlayerInventory) inventory).getBoots();
    }

    public ItemStack getOffhand () {
        return ((PlayerInventory) inventory).getItemInOffHand();
    }


    public void loadInventory() {

        Player target = Bukkit.getPlayer(this.uuid);

        if (target != null) {
            this.inventory = target.getInventory();
        } else {
            if (inventory == null) {
                inventory = Bukkit.createInventory(null, 4 * 9);
            }
            // Asynchronous
            huskAPI.getUser(getUuid()).thenAccept((oUser) -> {
                var snapshot = huskAPI.getLatestSnapshot(oUser.orElseThrow()).join().orElseThrow();
                var inv = ((BukkitData.Items.Inventory) snapshot.getInventory().orElseThrow());
                var isArray = inv.getContents(); // Den Inhalt des theoretischen Inventars
                inventory.setContents(isArray);
            });

        }

    }

    public void loadEnderchest() {

        Player target = Bukkit.getPlayer(this.uuid);

        if (target != null) {
            this.enderchst = target.getEnderChest();
        } else {
            if (enderchst == null) {
                enderchst = Bukkit.createInventory(null, InventoryType.ENDER_CHEST, Component.text(""));
            }
            // Asynchronous
            huskAPI.getUser(getUuid()).thenAccept((oUser) -> {
                var snapshot = huskAPI.getLatestSnapshot(oUser.orElseThrow()).join().orElseThrow();
                var inv = ((BukkitData.Items.EnderChest) snapshot.getEnderChest().orElseThrow());
                var isArray = inv.getContents(); // Den Inhalt des theoretischen Inventars
                enderchst.setContents(isArray);
            });

        }

    }

}
