package Listners;

import me.opkarol.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;

public class Kropka implements Listener {
    Main plugin;
    public String ACPREFIX = Objects.requireNonNull(Main.plugin.getConfig().getString("AC.Prefix"));
    public Kropka(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat3(AsyncPlayerChatEvent e) {
        String Wiadomosc = e.getMessage().replace(".","");
        String newMessage2 = Wiadomosc.toLowerCase();
        if(!e.getPlayer().hasPermission("wowchat.bypass.period") || !e.getPlayer().isOp()){
            if (newMessage2.length() > 3) {
                if (newMessage2.contains(".") || newMessage2.contains("?") || newMessage2.contains("!")) {
                    e.setMessage(("" + newMessage2.charAt(0)).toUpperCase() + newMessage2.substring(1));
                } else {
                    e.setMessage(("" + newMessage2.charAt(0)).toUpperCase() + newMessage2.substring(1) + ".");
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if(Main.plugin.getConfig().getBoolean("AC.Enable")) {
            String Message = e.getMessage();
            if (!p.hasPermission("wowchat.adminchat") || !p.isOp()) {
                if (Message.startsWith(ACPREFIX)) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Error") + Objects.requireNonNull(Main.plugin.getConfig().getString("Permission")).replace("%permission%", "wowchat.adminchat")));
                    e.setCancelled(true);
                }
            } else if (p.hasPermission("wowchat.adminchat") || p.isOp()) {
                if (Message.startsWith(ACPREFIX)) {
                    e.setCancelled(true);
                    for (Player p2 : Bukkit.getOnlinePlayers()) {
                        if (p2.hasPermission("wowchat.adminchat") || p2.isOp()) {
                            Message = Message.replaceFirst(ACPREFIX, "");
                            p2.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("AdminChat")).replace("%PLAYER%", p.getDisplayName()).replace("%MESSAGE%", Message)));
                        }
                    }
                }
            }
        }
    }
}