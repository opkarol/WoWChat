package GlobalChat;

import me.clip.placeholderapi.PlaceholderAPI;
import me.opkarol.main.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Objects;

import static Komendy.Chat.chat;
import static Listners.ChatDelay.*;
import static Others.ChannelSelection.Channel;
import static org.bukkit.Bukkit.getServer;

public class GlobalChat implements Listener {
    Main plugin;
    String GLOBAL = Main.plugin.getConfig().getString("GlobalChat.Start");
    String PREFIX = Main.plugin.getConfig().getString("GlobalChat.Prefix");
    String COLOR = Main.plugin.getConfig().getString("GlobalChat.ColorMessage");
    String LOCAL = Main.plugin.getConfig().getString("LocalChat.Start");

    HashMap<String, Boolean> map = new HashMap<String, Boolean>();

    public GlobalChat(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        map.put(p.getDisplayName(), false);
        String messageUpper = e.getMessage().toUpperCase();
        if(Main.plugin.getConfig().getBoolean("GlobalChat.Enabled") == true){
        if (messageUpper.startsWith(GLOBAL) || Channel.get(p.getDisplayName()) == 0 && !(messageUpper.startsWith(LOCAL))){
            if (chat == true) {
                if (canPlayerWrite == true) {
                    e.setCancelled(true);
                    String customcommand = Main.plugin.getConfig().getString("customcommand");
                    String message = e.getMessage().replace("<3", "❤").replace(":star:", "★").replace(":warn:", "⚠").replace(":)", "☺").replace(":(", "☹").replace(":/", "㋡");
                    String Message3 = message.replaceFirst(GLOBAL.toUpperCase(), "").replaceFirst(GLOBAL.toLowerCase(), "");
                    String MessageConfig = Main.plugin.getConfig().getString("GlobalChat.Message").replace("%G_Prefix%", PREFIX).replace("%PLAYER%", p.getDisplayName()).replace("%G_Color%", COLOR).replace("%MESSAGE%", Message3);
                    MessageConfig = PlaceholderAPI.setPlaceholders(e.getPlayer(), MessageConfig);
                    String adminmessage = "[ANTY-SWEAR] " + Main.plugin.getConfig().getString("messages.warnadmin").replace("%PLAYER%", e.getPlayer().getDisplayName()).replace("%MESSAGE%", e.getMessage());
                    String[] words = Objects.requireNonNull(Main.plugin.getConfig().getString("words")).toLowerCase().split(";");

                    for (int i = 0; i < words.length; ++i) {
                        if (Message3.toLowerCase().contains(words[i])) {
                            if (!p.hasPermission("wowchat.nocensor")) {
                                if (map.get(p.getDisplayName()) == false) {
                                    p.sendMessage((ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(Main.plugin.getConfig().getString("messages.warnplayer").replace("%PLAYER%", p.getName()).replace("%MESSAGE%", e.getMessage()).replace("%WORD%", words[i]))))));
                                    for (Player p2 : Bukkit.getOnlinePlayers()) {
                                        if (p2.hasPermission("wowchat.admin") || p2.isOp()) {
                                            p2.sendMessage(adminmessage);
                                        }
                                    }
                                    map.put(p.getDisplayName(), true);
                                    System.out.println(adminmessage);
                                    //Kick player
                                    if (Main.plugin.getConfig().getBoolean("kickoncensor")) {
                                        int finalI = i;
                                        Bukkit.getScheduler().runTask(plugin, new Runnable() {
                                            public void run() {
                                                p.kickPlayer(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("kickmessage").replace("%PLAYER%", p.getName()).replace("%MESSAGE%", e.getMessage()).replace("%WORD%", words[finalI])));
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
                    if (map.get(p.getDisplayName()) == false) {

                        Main.plugin.getLogger().warning(Main.plugin.getConfig().getString("GlobalChat.RawConsole.Prefix") + p.getDisplayName() + ":" + message.replaceFirst(GLOBAL.toLowerCase(), "").replaceFirst(GLOBAL.toUpperCase(), ""));
                        for (Player p2 : Bukkit.getOnlinePlayers()) {
                            p2.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageConfig));
                        }
                        map.put(p.getDisplayName(), false);

                    } else if (map.get(p.getDisplayName()) == null) {
                        System.out.println("[WOWCHAT] ERROR >> CAN'T CONNECT TO HASMAP, ERROR [000A1]");
                    }

                }
            } else {
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Error") + Main.plugin.getConfig().getString("YouCantChat")));
            }
        }
    }
}
}


