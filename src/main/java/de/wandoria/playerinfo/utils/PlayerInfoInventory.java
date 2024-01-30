package de.wandoria.playerinfo.utils;

import de.wandoria.playerinfo.Wandoria_PlayerInfo;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.wandoria.api.WandoriaRestApi;
import net.wandoria.api.WandoriaRestApiProvider;
import net.wandoria.api.account.Account;
import net.wandoria.api.level.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInfoInventory {

    // Config
    FileConfiguration config = Wandoria_PlayerInfo.plugin.getConfig();

    // Variables
    MiniMessage mm = MiniMessage.miniMessage();


    String banner = "";
    Player targetplayer = null;
    ItemStack head = null;
    ItemStack chestplate = null;
    ItemStack leggings = null;
    ItemStack boots = null;
    ItemStack offhand = null;
    Long level = null;
    Long money = null;
    Integer onlinetime = 0;

    public PlayerInfoInventory(String banner, Player targetplayer, ItemStack head, ItemStack chestplate, ItemStack leggings, ItemStack boots, ItemStack offhand, Long level, Long money, Integer onlinetime) {
        this.banner = banner;
        this.targetplayer = targetplayer;
        this.head = head;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
        this.offhand = offhand;
        this.level = level;
        this.money = money;
        this.onlinetime = onlinetime;
    }

    // Getter
    public String getBanner() {
        return banner;
    }

    public Player getTargetplayer() {
        return targetplayer;
    }

    public ItemStack getHead() {
        return head;
    }

    public ItemStack getChestplate() {
        return chestplate;
    }

    public ItemStack getLeggings() {
        return leggings;
    }

    public ItemStack getBoots() {
        return boots;
    }

    public ItemStack getOffhand() {
        return offhand;
    }

    public Long getLevel() {
        return level;
    }

    public Long getMoney() {
        return money;
    }

    public Integer getOnlinetime() {
        return onlinetime;
    }

    // Setter
    public void setBanner(String banner) {
        this.banner = banner;
    }

    public void setTargetplayer(Player targetplayer) {
        this.targetplayer = targetplayer;
    }

    public void setHead(ItemStack head) {
        this.head = head;
    }

    public void setChestplate(ItemStack chestplate) {
        this.chestplate = chestplate;
    }

    public void setLeggings(ItemStack leggings) {
        this.leggings = leggings;
    }

    public void setBoots(ItemStack boots) {
        this.boots = boots;
    }

    public void setOffhand(ItemStack offhand) {
        this.offhand = offhand;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public void setOnlinetime(Integer onlinetime) {
        this.onlinetime = onlinetime;
    }

    // Methods
    public void loadDatafromAPI() {

        WandoriaRestApi api = WandoriaRestApiProvider.get();
        Account account = api.getAccountHandler().get(getTargetplayer().getUniqueId()).orElse(null);
        setMoney(account.getBalance());

        Level level = api.getLevelHandler().get(getTargetplayer().getUniqueId()).orElse(null);
        setLevel(level.getLevel());

        setOnlinetime(null);

    }

    public Inventory generateInventory() {
        Inventory inventory = Bukkit.createInventory(getTargetplayer(), 54, getBanner());

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


        User user = new User(getTargetplayer().getUniqueId(), null, null, null);
        user.loadInventory();
        inventory.setItem(9, user.getHelmet());
        inventory.setItem(18, user.getChestplate());
        inventory.setItem(27, user.getLeggings());
        inventory.setItem(36, user.getBoots());
        inventory.setItem(45, user.getOffhand());


        while (inventory.firstEmpty() != -1){
            ItemStack filler = new ItemStack(Material.PAPER);
            ItemMeta fillerMeta = filler.getItemMeta();
            fillerMeta.displayName(mm.deserialize(""));
            fillerMeta.setCustomModelData(18001);
            filler.setItemMeta(fillerMeta);
            inventory.setItem(inventory.firstEmpty(), filler);
        }

        return inventory;

    }


    public Inventory generateSelectInventory() {
        Inventory inventory = Bukkit.createInventory(getTargetplayer(), 3*9, "Wähle eine Kategorie aus!");

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

        return inventory;
    }

}
