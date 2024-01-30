package de.wandoria.playerinfo.comnmads;

import de.wandoria.playerinfo.utils.PlayerInfoInventory;
import de.wandoria.playerinfo.utils.User;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerInfoCommand implements org.bukkit.command.CommandExecutor, org.bukkit.command.TabCompleter{

    MiniMessage mm = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;

            if (args.length == 0){

                PlayerInfoInventory playerInfoInventory = new PlayerInfoInventory("Hallo", p, null, null, null, null, null, null, null, 0);
                playerInfoInventory.loadDatafromAPI();
                p.sendMessage(mm.deserialize("<gray>[<blue>Wandoria<gray>] Deine Player Info wurde <green>geöffnet<gray>!"));

                p.openInventory(playerInfoInventory.generateInventory());
                return true;

            } else if (args.length == 1){

                Player target = p.getServer().getPlayer(args[0]);
                PlayerInfoInventory playerInfoInventory = new PlayerInfoInventory("Hallo 2", target, null, null, null, null, null, null, null, 0);
                playerInfoInventory.loadDatafromAPI();
                p.sendMessage(mm.deserialize("<gray>[<blue>Wandoria<gray>] Die PlayerInfo von <green>" + args[0] + " <gray>wurde <green>geöffnet<gray>!"));
                p.openInventory(playerInfoInventory.generateInventory());

                User user = new User(p.getUniqueId(), "banner", null, null);
                user.setTarget(target.getUniqueId());

                return true;

            }


        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        return null;
    }

}
