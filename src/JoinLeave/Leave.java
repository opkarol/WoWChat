package JoinLeave;

import me.clip.placeholderapi.PlaceholderAPI;
import me.opkarol.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class Leave implements Listener {
    Main plugin;
    public Leave(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLeave(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        if(p.hasPermission("Leave1")){
            String LeaveText = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("LeaveMessage.Leave1")));
            event.setQuitMessage(PlaceholderAPI.setPlaceholders(event.getPlayer(), LeaveText));
        }
        if(p.hasPermission("Leave2")){
            String LeaveText = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("LeaveMessage.Leave2")));
            event.setQuitMessage(PlaceholderAPI.setPlaceholders(event.getPlayer(), LeaveText));
        }
        if(p.hasPermission("Leave3")) {
            String LeaveText = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("LeaveMessage.Leave3")));
            event.setQuitMessage(PlaceholderAPI.setPlaceholders(event.getPlayer(), LeaveText));
        }
        if(p.hasPermission("Leave4")){
            String LeaveText = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("LeaveMessage.Leave4")));
            event.setQuitMessage(PlaceholderAPI.setPlaceholders(event.getPlayer(), LeaveText));
        }
        if(p.hasPermission("Leave5")){
            String LeaveText = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("LeaveMessage.Leave5")));
            event.setQuitMessage(PlaceholderAPI.setPlaceholders(event.getPlayer(), LeaveText));
        }

    }
}
