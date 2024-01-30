package de.wandoria.playerinfo.utils.inv;

import lombok.Getter;
import net.william278.husksync.data.BukkitData;
import net.william278.husksync.data.Data;
import net.william278.husksync.data.DataHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@Getter
public class HuskSyncInvHolder implements IndependantInventory {

    private final Inventory inventory;
    private final BukkitData.Items.Inventory huskInv;

    public HuskSyncInvHolder(BukkitData.Items.Inventory huskInv) {
        this.huskInv = huskInv;
        this.inventory = Bukkit.createInventory(null, 36);
        inventory.setContents(Arrays.copyOfRange(huskInv.getContents(), 0, 35));
    }


    @Override
    public ItemStack getBoots() {
        return huskInv.getContents()[(ARMOR_SLOTS.BOOTS)];
    }

    @Override
    public ItemStack getChestplate() {
        return huskInv.getContents()[ARMOR_SLOTS.CHESTPLATE];
    }

    @Override
    public ItemStack getHelmet() {
        return huskInv.getContents()[ARMOR_SLOTS.HELMET];
    }

    public ItemStack getLeggings() {
        return huskInv.getContents()[ARMOR_SLOTS.LEGGINS];
    }

    @Override
    public ItemStack getOffhand() {
        return huskInv.getContents()[ARMOR_SLOTS.OFFHAND];
    }

    interface ARMOR_SLOTS {
        int OFFHAND = 40;
        int HELMET = 39;
        int CHESTPLATE = 38;
        int LEGGINS = 37;
        int BOOTS = 36;
    }
}
