package de.wandoria.playerinfo.listener;

import de.wandoria.playerinfo.Wandoria_PlayerInfo;
import de.wandoria.playerinfo.inv.EnderchestView;
import de.wandoria.playerinfo.inv.PlayerInventoryView;
import de.wandoria.playerinfo.inv.SelectInventoryView;
import de.wandoria.playerinfo.utils.PlayerInfoInventory;
import de.wandoria.playerinfo.utils.Target;
import de.wandoria.playerinfo.utils.User;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryClickListener implements Listener {

    MiniMessage mm = MiniMessage.miniMessage();

    private final JavaPlugin javaPlugin;

    public InventoryClickListener() {
        this.javaPlugin = Wandoria_PlayerInfo.getPlugin();

    }


    @EventHandler
    public void onInventoryClick(org.bukkit.event.inventory.InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        var clickedInv = e.getClickedInventory().getHolder();

        System.out.println("Test0");
        if (clickedInv instanceof EnderchestView) {
            e.setCancelled(true); // Enderchest canceln
        }

        System.out.println("Test1");
        if (clickedInv instanceof SelectInventoryView) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null) {
                if (e.getClick().isLeftClick()) {
                    if (e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().getDisplayName().contains("Inventar")) {
                        p.closeInventory();

                        User user = new User(p.getUniqueId(), "banner", null, null);

                        Target target = new Target(user.getTargetPlayer().getUniqueId(), null, null);
                        // Asynchrones Laden des Inventars.
                        javaPlugin.getServer().getScheduler().runTask((Plugin) javaPlugin, (Runnable) target.loadInventory().thenAccept((inv) -> {
                            p.openInventory(inv.getInventory());
                        }).exceptionally((t) -> {
                            t.printStackTrace();
                            return null;
                        }));

                    } else if (e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().getDisplayName().contains("Enderchest")) {
                        p.closeInventory();
                        User user = new User(p.getUniqueId(), "banner", null, null);
                        Target target = new Target(user.getTargetPlayer().getUniqueId(), null, null);
                        target.loadEnderchest();
                        p.openInventory(target.getEnderchst().getInventory());

                    }
                }
            }
        }

        System.out.println("Test2");
        if (clickedInv instanceof PlayerInfoInventory) {
            System.out.println("Test2.1");
            e.setCancelled(true);
            System.out.println("Test2.2");
            if (e.getCurrentItem() != null) {
                System.out.println("Test2.3");
                if (e.getClick().isLeftClick()) {
                    System.out.println("Test2.4");
                    if (e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().getDisplayName().contains("Inventar")) {
                        System.out.println("Test2.5");
                        p.closeInventory();
                        PlayerInfoInventory playerInfoInventory = new PlayerInfoInventory("Hallo", null, null, null, null, null, null, null, null, 0);
                        p.openInventory(playerInfoInventory.generateSelectInventory());
                    }
                }
            }
        }
    }


}
