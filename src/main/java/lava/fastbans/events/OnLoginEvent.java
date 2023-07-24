package lava.fastbans.events;

import lava.fastbans.FastBans;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class OnLoginEvent implements Listener {
    @EventHandler
    public void onPlayerLoginEvent(AsyncPlayerPreLoginEvent event) {
        if (FastBans.isPlayerBanned(event.getUniqueId())) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, FastBans.formatColorCodes(FastBans.instance.getConfig().getString("banMessage").replace("%reason%", FastBans.getBanReason(event.getUniqueId()))));
        }
    }
}