package de.wandoria.playerinfo.comnmads;

import de.wandoria.playerinfo.Wandoria_PlayerInfo;
import de.wandoria.playerinfo.utils.PlayerInfoInventory;
import de.wandoria.playerinfo.utils.User;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.william278.husksync.api.BukkitHuskSyncAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;

public class PlayerInfoCommand implements org.bukkit.command.CommandExecutor, org.bukkit.command.TabCompleter{

    MiniMessage mm = MiniMessage.miniMessage();
    private final JavaPlugin javaPlugin;

    public PlayerInfoCommand() {
        this.javaPlugin = Wandoria_PlayerInfo.getPlugin();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p){
            if (args.length == 0){
                // Schreibweise 1: Komisch
                new PlayerInfoInventory(p).load().thenAccept((inv) -> {
                    System.out.println("Accepted");
                    javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> {
                        p.openInventory(inv);
                        p.sendMessage(mm.deserialize("<gray>[<blue>Wandoria<gray>] Deine Player Info wurde <green>geöffnet<gray>!"));
                        User user = new User(p.getUniqueId(), "banner", null, null);
                        user.setTarget(p.getUniqueId());
                    });
                });
                return true;
            } else if (args.length == 1){
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                new PlayerInfoInventory(target).load().thenAccept((inv) -> {
                    System.out.println("Accepted");
                    javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> {
                        p.openInventory(inv);
                        p.sendMessage(mm.deserialize("<gray>[<blue>Wandoria<gray>] Die PlayerInfo von <green>" + args[0] + " <gray>wurde <green>geöffnet<gray>!"));
                        User user = new User(p.getUniqueId(), "banner", null, null);
                        user.setTarget(target.getUniqueId());
                    });
                });

                return true;
            }
        }
        return true;
    }

    private static final int OFFLINE_PLAYER_LIMIT = 10;
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> result = new ArrayList<>();

        if (args.length == 1) {
            String playerName = args[0];
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().startsWith(playerName)) {
                    result.add(player.getName());
                }
            }
            if (result.isEmpty()) {
                for (OfflinePlayer op : Bukkit.getOfflinePlayers()) {
                    if (result.size() >= OFFLINE_PLAYER_LIMIT) {
                        break;
                    }
                    if (op.getName() != null && op.getName().startsWith(playerName)) {
                        result.add(op.getName());
                    }
                }
            }
        }
        return result;

    }

}
