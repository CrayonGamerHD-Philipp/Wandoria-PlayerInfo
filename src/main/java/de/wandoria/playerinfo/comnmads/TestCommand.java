package de.wandoria.playerinfo.comnmads;

import de.wandoria.playerinfo.utils.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TestCommand implements org.bukkit.command.CommandExecutor, org.bukkit.command.TabCompleter{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player p = (Player) commandSender;

        User user = new User(p.getUniqueId(), "banner", null, null);

        p.openInventory(user.getInventory());

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
