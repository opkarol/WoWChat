package Listners;

import me.opkarol.main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AntyZleSlowa implements Listener {
    Main plugin;

    public AntyZleSlowa(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.HIGH)
    public static void onChat2(AsyncPlayerChatEvent event) {
        String wiadomosc = event.getMessage();
        wiadomosc = wiadomosc.toLowerCase().replace("fuck", "****").replace("shit", "****");
        event.setMessage(wiadomosc);
    }
}
