package de.wandoria.playerinfo.utils;

import de.wandoria.playerinfo.Wandoria_PlayerInfo;
import de.wandoria.playerinfo.inv.EnderchestView;
import de.wandoria.playerinfo.inv.SelectInventoryView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.wandoria.api.WandoriaRestApi;
import net.wandoria.api.WandoriaRestApiProvider;
import net.wandoria.api.account.Account;
import net.wandoria.api.level.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

@Getter
@Setter
@AllArgsConstructor
public class PlayerInfoInventory implements InventoryHolder {

    // Config
    private final FileConfiguration config = Wandoria_PlayerInfo.plugin.getConfig();

    // Variables
    private final MiniMessage mm = MiniMessage.miniMessage();

    String banner = "";
    OfflinePlayer targetplayer = null;
    ItemStack head = null;
    ItemStack chestplate = null;
    ItemStack leggings = null;
    ItemStack boots = null;
    ItemStack offhand = null;
    Long level = null;
    Long money = null;
    Integer onlinetime = 0;


    public PlayerInfoInventory(OfflinePlayer target) {
        this.targetplayer = target;
    }

    public CompletableFuture<Inventory> load() {
        return CompletableFuture.supplyAsync(() -> {
            loadDatafromAPI();
            return generateInventory();
        }).exceptionally((t) -> {
            t.printStackTrace();
            return null;
        });
    }

    // Methods
    private void loadDatafromAPI() {
        WandoriaRestApi api = WandoriaRestApiProvider.get();
        Account account = api.getAccountHandler().get(getTargetplayer().getUniqueId()).orElse(null);
        setMoney(account.getBalance());
        Level level = api.getLevelHandler().get(getTargetplayer().getUniqueId()).orElse(null);
        setLevel(level.getLevel());
        setOnlinetime(null);
    }

    private Inventory generateInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, getBanner());

        ItemStack inventoryItem = new ItemBuilder(Material.getMaterial(config.getString("inventory.type")), 1).setDisplayName(mm.deserialize("<gray>Inventar")).setCustomModelData(config.getInt("inventory.custommodeldata")).toItemStack();
        inventory.setItem(config.getInt("inventory.slot"), inventoryItem);

        ItemStack levelItem = new ItemBuilder(Material.getMaterial(config.getString("level.type")), 1).setDisplayName(mm.deserialize("<gray>Level: " + getLevel())).setCustomModelData(config.getInt("level.custommodeldata")).toItemStack();
        inventory.setItem(config.getInt("level.slot"), levelItem);

        ItemStack moneyItem = new ItemBuilder(Material.getMaterial(config.getString("money.type")), 1).setDisplayName(mm.deserialize("<gray>Money: " + getMoney())).setCustomModelData(config.getInt("money.custommodeldata")).toItemStack();
        inventory.setItem(config.getInt("money.slot"), moneyItem);

        ItemStack onlinetimeItem = new ItemBuilder(Material.getMaterial(config.getString("onlinetime.type")), 1).setDisplayName(mm.deserialize("<red>Nicht verfügbar!")).setCustomModelData(config.getInt("onlinetime.custommodeldata")).toItemStack();
        inventory.setItem(config.getInt("onlinetime.slot"), onlinetimeItem);

        ItemStack friendsItem = new ItemStack(Material.getMaterial(config.getString("friends.type")));
        ItemMeta friendsItemMeta = friendsItem.getItemMeta();
        friendsItemMeta.displayName(mm.deserialize("<red>Nicht verfügbar!"));
        friendsItemMeta.setCustomModelData(config.getInt("friends.custommodeldata"));
        friendsItem.setItemMeta(friendsItemMeta);
        inventory.setItem(config.getInt("friends.slot"), friendsItem);

        ItemStack arenaItem = new ItemStack(Material.getMaterial(config.getString("arena.type")));
        ItemMeta arenaItemMeta = arenaItem.getItemMeta();
        arenaItemMeta.displayName(mm.deserialize("<red>Nicht verfügbar!"));
        arenaItemMeta.setCustomModelData(config.getInt("arena.custommodeldata"));
        arenaItem.setItemMeta(arenaItemMeta);
        inventory.setItem(config.getInt("arena.slot"), arenaItem);

        ItemStack pvpItem = new ItemStack(Material.getMaterial(config.getString("pvp.type")));
        ItemMeta pvpItemMeta = pvpItem.getItemMeta();
        pvpItemMeta.displayName(mm.deserialize("<red>Nicht verfügbar!"));
        pvpItemMeta.setCustomModelData(config.getInt("pvp.custommodeldata"));
        pvpItem.setItemMeta(pvpItemMeta);
        inventory.setItem(config.getInt("pvp.slot"), pvpItem);

        ItemStack planetItem = new ItemStack(Material.getMaterial(config.getString("planet.type")));
        ItemMeta planetItemMeta = planetItem.getItemMeta();
        planetItemMeta.displayName(mm.deserialize("<gray>Planet"));
        planetItemMeta.setCustomModelData(config.getInt("planet.custommodeldata"));
        planetItem.setItemMeta(planetItemMeta);
        inventory.setItem(config.getInt("planet.slot"), planetItem);

        ItemStack banHystoryItem = new ItemStack(Material.getMaterial(config.getString("banhystory.type")));
        ItemMeta banHystoryItemMeta = banHystoryItem.getItemMeta();
        banHystoryItemMeta.displayName(mm.deserialize("<red>Nicht verfügbar!"));
        banHystoryItemMeta.setCustomModelData(config.getInt("banhystory.custommodeldata"));
        banHystoryItem.setItemMeta(banHystoryItemMeta);
        inventory.setItem(config.getInt("banhystory.slot"), banHystoryItem);

        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.displayName(mm.deserialize("<gray>" + getTargetplayer().getName()));
        skullMeta.setOwningPlayer(getTargetplayer());
        playerHead.setItemMeta(skullMeta);
        inventory.setItem(9, playerHead);



        Target target = new Target(getTargetplayer().getUniqueId(), null, null);
        var inv = target.loadInventory().join();
        inventory.setItem(18, inv.getChestplate());
        inventory.setItem(27, inv.getLeggings());
        inventory.setItem(36, inv.getBoots());
        inventory.setItem(45, inv.getOffhand());
        while (inventory.firstEmpty() != -1){
            ItemStack filler = new ItemStack(Material.PAPER);
            ItemMeta fillerMeta = filler.getItemMeta();
            fillerMeta.displayName(Component.text(""));
            fillerMeta.setCustomModelData(18001);
            filler.setItemMeta(fillerMeta);
            inventory.setItem(inventory.firstEmpty(), filler);
        }
        return inventory;

    }


    public Inventory generateSelectInventory() {
        Inventory inventory = new SelectInventoryView().getInventory();
        ItemStack enderchestItem = new ItemStack(Material.ENDER_CHEST);
        ItemMeta enderchestItemMeta = enderchestItem.getItemMeta();
        enderchestItemMeta.displayName(mm.deserialize("<gray>Enderchest"));
        enderchestItemMeta.setCustomModelData(1);
        enderchestItem.setItemMeta(enderchestItemMeta);
        inventory.setItem(12, enderchestItem);

        ItemStack innventoryItem = new ItemStack(Material.CHEST);
        ItemMeta innventoryItemMeta = innventoryItem.getItemMeta();
        innventoryItemMeta.displayName(mm.deserialize("<gray>Inventar"));
        innventoryItemMeta.setCustomModelData(1);
        innventoryItem.setItemMeta(innventoryItemMeta);
        inventory.setItem(14, innventoryItem);

        while (inventory.firstEmpty() != -1){
            ItemStack filler = new ItemStack(Material.PAPER);
            ItemMeta fillerMeta = filler.getItemMeta();
            fillerMeta.displayName(Component.text(""));
            fillerMeta.setCustomModelData(18001);
            filler.setItemMeta(fillerMeta);
            inventory.setItem(inventory.firstEmpty(), filler);
        }

        return inventory;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
