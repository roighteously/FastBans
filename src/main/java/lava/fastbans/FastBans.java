package lava.fastbans;

import lava.fastbans.cmds.*;
import lava.fastbans.events.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class FastBans extends JavaPlugin {
    private static FileConfiguration data;
    public static FastBans instance;
    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        saveConfig();
        new BansFile();
        data = BansFile.getConfig();
        getCommand("ban").setExecutor(new BanCommand());
        getCommand("unban").setExecutor(new UnbanCommand());
        getServer().getPluginManager().registerEvents(new OnLoginEvent(), this);
    }

    public static void writeBan(Player player, String reason) {
        data.set(String.valueOf(player.getUniqueId()), String.valueOf(reason));
        BansFile.saveConfig();
    }

    public static void writeTempBan(Player player, String reason, int year, int month, int day, int hour, int minute) {
        ConfigurationSection bansSection = data.getConfigurationSection("temp");
        if (bansSection == null) {
            bansSection = data.createSection("bans");
        }
        ConfigurationSection playerSection = bansSection.createSection(player.getUniqueId().toString());
        playerSection.set("reason", reason);
        Calendar expirationTime = Calendar.getInstance(TimeZone.getTimeZone("EST"));
        expirationTime.set(year, month - 1, day, hour, minute);
        long expiresAt = expirationTime.getTimeInMillis();
        playerSection.set("expiresAt", expiresAt);
        BansFile.saveConfig();
    }

    public static void unban(UUID uuid, CommandSender sender, String name) {
        if (isPlayerBanned(uuid)) {
            data.set(String.valueOf(uuid), null);
            BansFile.saveConfig();
            sender.sendMessage(format("<red>Unbanned <green>" + name));
        } else {
            sender.sendMessage(format("<red>Player is not banned"));
        }
    }

    public static String getBanReason(UUID uuid) {
        return data.getString(String.valueOf(uuid));
    }

    public static boolean isPlayerBanned(UUID uuid) {
        if (data.getString(String.valueOf(uuid)) != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Component format(String text) { return MiniMessage.miniMessage().deserialize(text); }

    public static String convertColorCodesToAdventure(String text) {
        final String[] last = {text};
        codeToAdventure.forEach((code, adv) -> last[0] = last[0].replaceAll(code, adv));
        return last[0];
    }

    public static String convertAdventureToColorCodes(String text) {
        final String[] last = {text};
        codeToAdventure.forEach((code, adv) -> last[0] = last[0].replaceAll(adv, code));
        return last[0];
    }

    public static String formatColorCodes(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static final Map<String, String> codeToAdventure = new HashMap<>(){{
        put("&0", "<black>");
        put("&1", "<dark_blue>");
        put("&2", "<dark_green>");
        put("&3", "<dark_aqua>");
        put("&4", "<dark_red>");
        put("&5", "<dark_purple>");
        put("&6", "<gold>");
        put("&7", "<gray>");
        put("&8", "<dark_gray>");
        put("&9", "<blue>");
        put("&a", "<green>");
        put("&b", "<aqua>");
        put("&c", "<red>");
        put("&d", "<light_purple>");
        put("&e", "<yellow>");
        put("&f", "<white>");
        put("&k", "<obf>");
        put("&l", "<b>");
        put("&m", "<st>");
        put("&n", "<u>");
        put("&o", "<i>");
        put("&r", "<reset>");
    }};
}