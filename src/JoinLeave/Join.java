package JoinLeave;

import me.clip.placeholderapi.PlaceholderAPI;
import me.opkarol.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class Join implements Listener {
    Main plugin;
    public Join(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if(p.hasPermission("Join1")){
            String joinText = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("JoinMessage.Join1")));
            event.setJoinMessage(PlaceholderAPI.setPlaceholders(event.getPlayer(), joinText));
        }
        if(p.hasPermission("Join2")){
            String joinText = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("JoinMessage.Join2")));
            event.setJoinMessage(PlaceholderAPI.setPlaceholders(event.getPlayer(), joinText));
        }
        if(p.hasPermission("Join3")) {
            String joinText = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("JoinMessage.Join3")));
            event.setJoinMessage(PlaceholderAPI.setPlaceholders(event.getPlayer(), joinText));
        }
        if(p.hasPermission("Join4")){
            String joinText = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("JoinMessage.Join4")));
            event.setJoinMessage(PlaceholderAPI.setPlaceholders(event.getPlayer(), joinText));
        }
        if(p.hasPermission("Join5")){
            String joinText = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("JoinMessage.Join5")));
            event.setJoinMessage(PlaceholderAPI.setPlaceholders(event.getPlayer(), joinText));
        }

    }
}
