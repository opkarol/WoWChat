
package GlobalChat;

import java.util.*;

import me.clip.placeholderapi.PlaceholderAPI;
import me.opkarol.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static Komendy.Chat.chat;
import static Listners.ChatDelay.canPlayerWrite;
import static Others.ChannelSelection.Channel;
import static org.bukkit.Bukkit.getServer;

public class ChatListener implements Listener {
    Main plugin;
    String LOCAL = Main.plugin.getConfig().getString("LocalChat.Start");
    String PREFIX = Main.plugin.getConfig().getString("LocalChat.Prefix");
    String COLOR = Main.plugin.getConfig().getString("LocalChat.ColorMessage");
    String GLOBAL = Main.plugin.getConfig().getString("GlobalChat.Start");

    public ChatListener(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    public static ArrayList<Player> bypass = new ArrayList();
    HashMap<String, Boolean> map = new HashMap<String, Boolean>();


    @EventHandler(priority = EventPriority.LOWEST)
    public void chat(AsyncPlayerChatEvent event) {


    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player sender = e.getPlayer();
        if (Main.plugin.getConfig().getBoolean("LocalChat.Enabled")) {
            Iterator var3 = (new HashSet(e.getRecipients())).iterator();
            while (var3.hasNext()) {
                Player receiver = (Player) var3.next();

                if (sender.getWorld() != receiver.getWorld()) {
                    e.getRecipients().remove(receiver);
                } else {
                    if (!this.inRange(getDistance(sender.getLocation(), receiver.getLocation())) && !bypass.contains(receiver)) {
                        e.getRecipients().remove(receiver);
                    }
                }

            }


            Player p = e.getPlayer();
            map.put(p.getDisplayName(), false);
            String messageUpper = e.getMessage().toUpperCase();
            if (messageUpper.startsWith(LOCAL) || Channel.get(p.getDisplayName()) == 1 && !(messageUpper.startsWith(GLOBAL))) {
                if (chat == true) {
                    if (canPlayerWrite == true) {
                        e.setCancelled(true);
                        String customcommand = Main.plugin.getConfig().getString("customcommand");
                        String message = e.getMessage().replace("<3", "❤").replace(":star:", "★").replace(":warn:", "⚠").replace(":)", "☺").replace(":(", "☹").replace(":/", "㋡");
                        String Message3 = message.replaceFirst(LOCAL.toUpperCase(), "").replaceFirst(LOCAL.toLowerCase(), "");
                        String MessageConfig = Main.plugin.getConfig().getString("LocalChat.Message").replace("%L_Prefix%", PREFIX).replace("%PLAYER%", p.getDisplayName()).replace("%L_Color%", COLOR).replace("%MESSAGE%", Message3);
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
                                        map.put(p.getDisplayName(), true);
                                        System.out.println(adminmessage);

                                    }
                                }
                            }
                        }
                        if (map.get(p.getDisplayName()) == false) {

                            Main.plugin.getLogger().warning(Main.plugin.getConfig().getString("LocalChat.RawConsole.Prefix") + p.getDisplayName() + ":" + message.replaceFirst(LOCAL.toLowerCase(), "").replaceFirst(LOCAL.toUpperCase(), ""));
                            for (Player p2 : Bukkit.getOnlinePlayers()) {
                                if (e.getRecipients().contains(p2)) {
                                    p2.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageConfig));
                                }
                            }
                            map.put(p.getDisplayName(), false);

                        } else if (map.get(p.getDisplayName()) == null) {
                            System.out.println("[WOWCHAT] ERROR >> CAN'T CONNECT TO HASMAP, ERROR [000B1]");
                        }

                    }
                } else {
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Error") + Main.plugin.getConfig().getString("YouCantChat")));
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
