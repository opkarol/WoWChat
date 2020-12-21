
package GlobalChat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import me.opkarol.main.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    Main plugin;
    String PREFIX = Main.plugin.getConfig().getString("LocalChat.Prefix");
    String COLOR = Main.plugin.getConfig().getString("LocalChat.ColorMessage");

    public ChatListener(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    public static ArrayList<Player> bypass = new ArrayList();


    @EventHandler(
            priority = EventPriority.LOWEST
    )
    public void chat(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();
        if (Main.plugin.getConfig().getBoolean("LocalChat.Enabled")) {
            Iterator var3 = (new HashSet(event.getRecipients())).iterator();
            while(var3.hasNext()) {
                Player receiver = (Player)var3.next();

                if (sender.getWorld() != receiver.getWorld()){
                    event.getRecipients().remove(receiver);
                } else {
                    if (!this.inRange(getDistance(sender.getLocation(), receiver.getLocation())) && !bypass.contains(receiver)) {
                        event.getRecipients().remove(receiver);
                    }
                }

            }
        }

    }

    private static double getDistance(Location sender, Location reciever) {
        double distance = sender.distance(reciever);
        return distance;
    }
    private boolean inRange(double distance) {
        return distance < (double)Main.plugin.getConfig().getInt("LocalChat.Range");
    }
}
