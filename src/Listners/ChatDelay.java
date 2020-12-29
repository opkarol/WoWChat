package Listners;

import me.opkarol.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

import static java.lang.System.currentTimeMillis;

public class ChatDelay implements Listener {
    public int ChatDelay = Main.plugin.getConfig().getInt("ChatDelayInSeconds");
    public Main plugin;
    public static boolean canPlayerWrite;
    public String bold = ChatColor.BOLD + "";
    public ChatDelay(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    HashMap<UUID, Long> delayMap = new HashMap<>();
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(Main.plugin.getConfig().getBoolean ("ChatDelay")){
            if (!event.getPlayer().hasPermission("wowchat.bypass.delay") || !event.getPlayer().isOp()) {
                UUID id = event.getPlayer().getUniqueId();
                if (!delayMap.containsKey(id)) {
                    delayMap.put(id, currentTimeMillis());
                    return;
                }

                long lastChat = delayMap.get(id);
                long now = currentTimeMillis();
                long diff = now - lastChat;
                if (!(diff >= Main.plugin.getConfig().getInt("ChatDelayInSeconds")*1000)){
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Error") + Main.plugin.getConfig().getString("ChatDelayWarn").replace("%seconds%", String.valueOf(ChatDelay))));
                    event.setCancelled(true);
                } else{
                    event.setCancelled(false);
                }
                delayMap.put(id, currentTimeMillis());
                if (!(diff >= Main.plugin.getConfig().getInt("ChatDelayInSeconds")*1000)){
                    canPlayerWrite = false;
                } else{
                    canPlayerWrite = true;
                }
            }
        }
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UUID id = event.getPlayer().getUniqueId();
        if (delayMap.containsKey(id)) {
            delayMap.remove(id);
        }
    }
    @EventHandler
    public void onKick(PlayerKickEvent event) {
        UUID id = event.getPlayer().getUniqueId();
        if (delayMap.containsKey(id)) {
            delayMap.remove(id);
        }
    }
}