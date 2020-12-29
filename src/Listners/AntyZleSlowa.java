package Listners;

import me.opkarol.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import static Listners.ChatDelay.canPlayerWrite;
import static Others.ChannelSelection.Channel;
import static org.bukkit.Bukkit.getServer;

public class AntyZleSlowa implements Listener {
    Main plugin;
    String GLOBAL = Main.plugin.getConfig().getString("GlobalChat.Start");
    HashMap<String, Boolean> map = new HashMap<String, Boolean>();
    //map = true, jesli gracz przeklnal
    //map = false, jesli nie przeklnal

    public AntyZleSlowa(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat2(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        map.put(p.getDisplayName(), false);

        String messageUpper = event.getMessage().toUpperCase();
        String customcommand = Main.plugin.getConfig().getString("customcommand");
        String[] words = Objects.requireNonNull(Main.plugin.getConfig().getString("words")).toLowerCase().split(";");
        String msg = event.getMessage();
        String message = "[ANTY-SWEAR] " + Main.plugin.getConfig().getString("messages.warnadmin").replace("%PLAYER%", event.getPlayer().getDisplayName()).replace("%MESSAGE%", msg);
        if(Main.plugin.getConfig().getBoolean("")) {
            p.sendMessage(String.valueOf(Channel.get(p.getDisplayName())));
            if (!messageUpper.startsWith(GLOBAL)) {
                if (canPlayerWrite == true) {
                    for (int i = 0; i < words.length; ++i) {
                        if (!p.hasPermission("wowchat.nocensor")) {
                            if (msg.toLowerCase().contains(words[i])) {
                                if (map.get(p.getDisplayName()) == false) {
                                    p.sendMessage((ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(Main.plugin.getConfig().getString("messages.warnplayer").replace("%PLAYER%", p.getName()).replace("%MESSAGE%", event.getMessage()).replace("%WORD%", words[i]))))));
                                    for (Player p2 : Bukkit.getOnlinePlayers()) {
                                        if (p2.hasPermission("wowchat.admin") || p2.isOp()) {
                                            p2.sendMessage(message);
                                        }
                                    }
                                    map.put(p.getDisplayName(), true);
                                    //Kick player
                                    if (Main.plugin.getConfig().getBoolean("kickoncensor")) {
                                        int finalI = i;
                                        Bukkit.getScheduler().runTask(plugin, new Runnable() {
                                            public void run() {
                                                p.kickPlayer(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("kickmessage").replace("%PLAYER%", p.getName()).replace("%MESSAGE%", event.getMessage()).replace("%WORD%", words[finalI])));
                                            }
                                        });
                                    }
                                    //Config, custom command
                                    Bukkit.getScheduler().runTask(plugin, new Runnable() {
                                        public void run() {
                                            getServer().dispatchCommand(getServer().getConsoleSender(), customcommand);
                                        }
                                    });
                                }

                            }
                        }
                    }
                    if (map.get(p.getDisplayName()) == true) {
                        event.setCancelled(true);
                        System.out.println(message);

                    } else if (map.get(p.getDisplayName()) == null) {
                        System.out.println("[WOWCHAT] ERROR");
                    }
                }
            }
        }
    }
}
