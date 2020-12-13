package Listners;

import me.opkarol.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Kropka implements Listener {
    Main plugin;

    public Kropka(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat3(AsyncPlayerChatEvent e) {
        String Wiadomosc = e.getMessage().replace(".","");
        String newMessage2 = Wiadomosc.toLowerCase();
        if (newMessage2.length() > 3) {
            if (newMessage2.contains(".") || newMessage2.contains("?") || newMessage2.contains("!")){
                e.setMessage(("" + newMessage2.charAt(0)).toUpperCase() + newMessage2.substring(1));
            }else{
                e.setMessage(("" + newMessage2.charAt(0)).toUpperCase() + newMessage2.substring(1) + ".");
            }
        }
    }
}