package GlobalChat;

import me.clip.placeholderapi.PlaceholderAPI;
import me.opkarol.main.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class GlobalChat implements Listener {
    Main plugin;
    String GLOBAL = Main.plugin.getConfig().getString("GlobalChat.Start");
    String PREFIX = Main.plugin.getConfig().getString("GlobalChat.Prefix");
    String COLOR = Main.plugin.getConfig().getString("GlobalChat.ColorMessage");

    public GlobalChat(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        String message = e.getMessage().replace("<3", "❤").replace(":star:", "★").replace(":warn:", "⚠").replace(":)", "☺").replace(":(", "☹").replace(":/", "㋡");
        String messageUpper = e.getMessage().toUpperCase();
        String Message3 = message.replaceFirst(GLOBAL, "").replaceFirst(GLOBAL.toLowerCase(), "");
        String MessageConfig = Main.plugin.getConfig().getString("GlobalChat.Message").replace("%G_Prefix%", PREFIX).replace("%PLAYER%", p.getDisplayName()).replace("%G_Color%", COLOR).replace("%MESSAGE%", Message3 );
        MessageConfig = PlaceholderAPI.setPlaceholders(e.getPlayer(), MessageConfig);
        if (messageUpper.startsWith(GLOBAL)){
            e.setCancelled(true);
            Main.plugin.getLogger().warning(Main.plugin.getConfig().getString("GlobalChat.RawConsole.Prefix") + p.getDisplayName() + ":" + message.replaceFirst("!G", "").replaceFirst("!g", ""));
            for(Player p2 : Bukkit.getOnlinePlayers()){
                p2.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageConfig));
            }
        }

    }
}
