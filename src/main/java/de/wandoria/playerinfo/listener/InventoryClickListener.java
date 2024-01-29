package de.wandoria.playerinfo.listener;

import de.wandoria.playerinfo.utils.PlayerInfoInventory;
import de.wandoria.playerinfo.utils.User;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class InventoryClickListener implements Listener {

    MiniMessage mm = MiniMessage.miniMessage();


    @EventHandler
    public void onInventoryClick(org.bukkit.event.inventory.InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equalsIgnoreCase("Hallo") || e.getView().getTitle().equalsIgnoreCase("Hallo 2")) {

            e.setCancelled(true);

            if (e.getCurrentItem() != null) {
                if (e.getClick().isLeftClick()) {
                    if (e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().getDisplayName().contains("Inventar")){
                        p.closeInventory();

                        PlayerInfoInventory playerInfoInventory = new PlayerInfoInventory("Hallo", p, null, null, null, null, null, null, null, 0);
                        p.openInventory(playerInfoInventory.generateSelectInventory());

                    }
                }
            }
        } else if (e.getView().getTitle().equalsIgnoreCase("Wähle eine Kategorie aus!")){

            e.setCancelled(true);

            if (e.getCurrentItem() != null) {
                if (e.getClick().isLeftClick()) {
                    if (e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().getDisplayName().contains("Inventar")){
                        p.closeInventory();

                        User user = new User(p.getUniqueId(), "banner", null, null);
                        user.loadInventory();
                        p.openInventory(user.getInventory());

                    } else if (e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().getDisplayName().contains("Enderchest")){
                        p.closeInventory();

                        User user = new User(p.getUniqueId(), "banner", null, null);
                        user.loadEnderchest();
                        p.openInventory(user.getEnderchst());

                    }
                }
            }
        } else if (e.getView().getTitle().equalsIgnoreCase("Player") || e.getView().getTitle().equalsIgnoreCase("Ender Chest")){

            e.setCancelled(true);

        }

    }


}
