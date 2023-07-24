package lava.fastbans.cmds;

import lava.fastbans.FastBans;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class UnbanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String argument = args.length > 0 ? args[0] : "";
        FastBans.unban(Bukkit.getOfflinePlayer(argument).getUniqueId(), sender, argument);
        return true;
    }
}