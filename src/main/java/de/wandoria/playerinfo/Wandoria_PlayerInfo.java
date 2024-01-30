package de.wandoria.playerinfo;

import de.wandoria.playerinfo.utils.MySQL;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Wandoria_PlayerInfo extends JavaPlugin {

    // Config
    FileConfiguration config = getConfig();

    // Plugin
    @Getter
    public static Wandoria_PlayerInfo plugin;


    @Override
    public void onEnable() {
        plugin = this;
        // Config
        config.addDefault("inventory.type", "ENDER_CHEST");
        config.addDefault("inventory.custommodeldata", 1);
        config.addDefault("inventory.slot", 11);

        config.addDefault("level.type", "SPYGLASS");
        config.addDefault("level.custommodeldata", 1);
        config.addDefault("level.slot", 13);

        config.addDefault("money.type", "SUNFLOWER");
        config.addDefault("money.custommodeldata", 1);
        config.addDefault("money.slot", 15);

        config.addDefault("onlinetime.type", "CLOCK");
        config.addDefault("onlinetime.custommodeldata", 1);
        config.addDefault("onlinetime.slot", 17);

        config.addDefault("friends.type", "NETHERITE_AXE");
        config.addDefault("friends.custommodeldata", 1);
        config.addDefault("friends.slot", 38);

        config.addDefault("arena.type", "LEAD");
        config.addDefault("arena.custommodeldata", 1);
        config.addDefault("arena.slot", 40);

        config.addDefault("pvp.type", "IRON_SWORD");
        config.addDefault("pvp.custommodeldata", 1);
        config.addDefault("pvp.slot", 42);

        config.addDefault("planet.type", "OAK_LEAVES");
        config.addDefault("planet.custommodeldata", 1);
        config.addDefault("planet.slot", 44);

        config.addDefault("banhystory.type", "REDSTONE_BLOCK");
        config.addDefault("banhystory.custommodeldata", 1);
        config.addDefault("banhystory.slot", 53);

        config.addDefault("mysql.host", "localhost");
        config.addDefault("mysql.port", "3306");
        config.addDefault("mysql.database", "wandoria");
        config.addDefault("mysql.username", "root");
        config.addDefault("mysql.password", "password");
        config.options().copyDefaults(true);
        saveConfig();

        // Register Commands
        getCommand("playerinfo").setExecutor(new de.wandoria.playerinfo.comnmads.PlayerInfoCommand());
        getCommand("test").setExecutor(new de.wandoria.playerinfo.comnmads.TestCommand());

        // Register TabCompleter
        getCommand("playerinfo").setTabCompleter(new de.wandoria.playerinfo.comnmads.PlayerInfoCommand());

        // Register Listener
        Bukkit.getPluginManager().registerEvents(new de.wandoria.playerinfo.listener.InventoryClickListener(), this);

        // Return Plugin


        // Create MySQL Connection
        //MySQL mySQL = new MySQL(config.getString("mysql.host"), config.getString("mysql.port"), config.getString("mysql.database"), config.getString("mysql.username"), config.getString("mysql.password"));


        // Enable Plugin
        Bukkit.getConsoleSender().sendMessage("§7[§9Wandoria§7] §7Das Plugin §aWandoria PlayerInfo §7wurde erfolgreich aktiviert!");


    }

    @Override
    public void onDisable() {
        // Close MySQL Connection
        //MySQL mySQL = new MySQL(null, null, null, null, null);
        //mySQL.disconnect();
    }
}
