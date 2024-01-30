package de.wandoria.playerinfo.utils;

import de.wandoria.playerinfo.inv.EnderchestView;
import de.wandoria.playerinfo.utils.inv.HuskSyncInvHolder;
import de.wandoria.playerinfo.utils.inv.IndependantInventory;
import de.wandoria.playerinfo.utils.inv.PlayerInventoryHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.william278.husksync.api.BukkitHuskSyncAPI;
import net.william278.husksync.api.HuskSyncAPI;
import net.william278.husksync.data.BukkitData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
@Setter
@AllArgsConstructor
public class Target {

    private final HuskSyncAPI huskAPI = BukkitHuskSyncAPI.getInstance();

    UUID uuid;
    IndependantInventory inventory;
    EnderchestView enderchst;


    // Setter
    public ItemStack getHelmet( ) {
        return inventory.getHelmet();
    }

    public ItemStack getChestplate () {
        return inventory.getChestplate();
    }

    public ItemStack getLeggings () {
        return inventory.getLeggings();
    }

    public ItemStack getBoots () {
        return inventory.getBoots();
    }

    public ItemStack getOffhand () {
        return inventory.getOffhand();
    }


    // Methods
    public CompletableFuture<IndependantInventory> loadInventory() {
        CompletableFuture<IndependantInventory> future = new CompletableFuture<>();
        Player target = Bukkit.getPlayer(this.uuid);
        if (target != null) {
            this.inventory = new PlayerInventoryHolder(target.getInventory());
            future.complete(this.inventory);
        } else {
            // Asynchronous
            huskAPI.getUser(getUuid()).thenAcceptAsync((oUser) -> {
                var snapshot = huskAPI.getCurrentData(oUser.orElseThrow()).join().orElseThrow();
                var inv = ((BukkitData.Items.Inventory) snapshot.getInventory().orElseThrow());
                inventory = new HuskSyncInvHolder(inv);
            }).handle((r, t) -> {
                if (t != null) {
                    handleAsyncException(t);
                    future.completeExceptionally(t);
                }
                future.complete(inventory);
                return null;
            });
        }
        return future;
    }

    public void loadEnderchest() {

        Player target = Bukkit.getPlayer(this.uuid);

        if (target != null) {
            this.enderchst = new EnderchestView(target.getEnderChest());
        } else {
            if (enderchst == null) {
                enderchst = new EnderchestView();
            }
            // Asynchronous
            huskAPI.getUser(getUuid()).thenAcceptAsync((oUser) -> {
                var snapshot = huskAPI.getCurrentData(oUser.orElseThrow()).join().orElseThrow();
                var inv = ((BukkitData.Items.EnderChest) snapshot.getEnderChest().orElseThrow());
                var isArray = inv.getContents(); // Den Inhalt des theoretischen Inventars
                enderchst.getInventory().setContents(isArray);
            }).exceptionally(this::handleAsyncException);
        }
    }

    private Void handleAsyncException(Throwable t) {
        t.printStackTrace();
        return null;
    }


}
