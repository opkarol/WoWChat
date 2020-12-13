package Listners;

import me.opkarol.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Kolorki implements Listener {
    Main plugin;

    public Kolorki(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent event2) {
        if(Main.plugin.getConfig().getBoolean("ChatFormatting")){
            Player p = event2.getPlayer();
            if (p.hasPermission("wow.chat.colorcodes") || p.isOp()) {
                String pobieranieWiadomosci = event2.getMessage();
                String nowaWiadomosc = ChatColor.translateAlternateColorCodes('&', pobieranieWiadomosci);
                event2.setMessage(nowaWiadomosc);
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onChat2(AsyncPlayerChatEvent event2) {
        Player p = event2.getPlayer();
        if(Main.plugin.getConfig().getBoolean("UnicodeEmojis")){
            if (p.hasPermission("wow.chat.unicodeemojis") || p.isOp()) {
                event2.setMessage(event2.getMessage().replace("<3", "❤").replace(":star:", "★").replace(":warn:", "⚠").replace(":)", "☺").replace(":(", "☹").replace(":/", "㋡"));
            }
        }
    }
}
