package Listners;

import me.opkarol.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.Objects;

public class AntyZleSlowa implements Listener {
    Main plugin;
    static Plugin pg = JavaPlugin.getPlugin(Main.class);
    public AntyZleSlowa(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.HIGH)
    public static void onChat2(AsyncPlayerChatEvent event) {
        String[] words = Objects.requireNonNull(Main.plugin.getConfig().getString("words")).toLowerCase().split(";");
        String msg = event.getMessage();
        Player p = event.getPlayer();
        String message =  "[WOWCHAT-ANTY SWEAR] " + Main.plugin.getConfig().getString("messages.warnadmin").replace("%PLAYER%", event.getPlayer().getDisplayName()).replace("%MESSAGE%", msg);
        for(int i = 0; i < words.length; ++i) {
            if (msg.toLowerCase().contains(words[i])) {
                if (!p.hasPermission("wowchat.nocensor")) {
                    if(Main.plugin.getConfig().getBoolean("cancelEvent")){
                        event.setCancelled(true);
                    }
                    System.out.println(message);
                    p.sendMessage((ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(Main.plugin.getConfig().getString("messages.warnplayer").replace("%PLAYER%", p.getName()).replace("%MESSAGE%", event.getMessage()).replace("%WORD%", words[i]))))));
                    for(Player p2 : Bukkit.getOnlinePlayers()){
                        if(p2.hasPermission("wowchat.admin") || p2.isOp()){
                            p2.sendMessage(message);
                        }
                    }
                }
            }
        }
    }
}
