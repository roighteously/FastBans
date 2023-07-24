package lava.fastbans.cmds;

import lava.fastbans.FastBans;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String argument = args.length > 0 ? args[0] : "";
        Player player = Bukkit.getPlayer(argument);
        if (player == null) {
            sender.sendMessage(FastBans.format("<red>Invalid player"));
            return true;
        }
        String reason = args.length > 1 ? args[1] : FastBans.instance.getConfig().getString("defaultBanReason");
        FastBans.writeBan(player, reason);
        player.kickPlayer(FastBans.formatColorCodes(FastBans.instance.getConfig().getString("banMessage").replace("%reason%", reason)));
        return true;
    }
}