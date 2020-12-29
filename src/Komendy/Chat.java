package Komendy;

import me.opkarol.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.Objects;

public class Chat implements Listener, CommandExecutor {
    public static boolean chat = true;
    Inventory inventory = GUIs.Chat.openGUI();
    Main plugin;
    public String bold = ChatColor.BOLD + "";
    public String perm1 = "wowchat.admin";
    public Chat(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("chat")).setExecutor(this);
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender != null && cmd.getName().equalsIgnoreCase("chat")) {
            Player p = (Player) sender;
            if (!p.hasPermission(perm1) || !p.isOp()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Error") + Main.plugin.getConfig().getString("Permission").replace("%permission%", perm1)));
            } if (p.hasPermission(perm1) || p.isOp()) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("clear")) {
                        for (int i = 0; i < 101; ++i) {
                            for (Player p2 : Bukkit.getOnlinePlayers()) {
                                p2.sendMessage(" ");
                            }
                        }
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("ChatClear.Line1").replace("%PLAYER%", p.getName()))));
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("ChatClear.Line2").replace("%PLAYER%", p.getName()))));
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("ChatClear.Line3").replace("%PLAYER%", p.getName()))));
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("ChatClear.Line4").replace("%PLAYER%", p.getName()))));
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("ChatClear.Line5").replace("%PLAYER%", p.getName()))));
                    } else if (args[0].equalsIgnoreCase("on")) {
                        if (!chat) {
                            chat = true;
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("ChatTurnOn"))));
                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("GlobalChatTurnOn").replace("%playerchat%", p.getName())));
                            return true;
                        }

                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Error") + Main.plugin.getConfig().getString("ChatOn")));
                        return false;
                    } else if (args[0].equalsIgnoreCase("gui")) {
                        p.sendMessage("INCOMING IN Bv0.6");
                    } else if (args[0].equalsIgnoreCase("off")) {
                        if (chat) {
                            chat = false;
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("ChatTurnOff"))));
                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("GlobalChatTurnOff")).replace("%playerchat%", p.getName())));
                            return false;
                        }
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Error") + Main.plugin.getConfig().getString("ChatOff")));
                        return true;
                    } else if (args[0].equalsIgnoreCase("help")) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lWOWCHAT &7- &e&lHELP"));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aChatDelay&7: &e" + Main.plugin.getConfig().getString("ChatDelay")));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aChatDelayInSeconds&7: &b" + Main.plugin.getConfig().getString("ChatDelayInSeconds")));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aChatFormatting&7: &e" + Main.plugin.getConfig().getString("ChatFormatting")));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aDebugModeLevel&7: &b" + Main.plugin.getConfig().getString("DebugModeLevel")));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aUnicodeEmojis&7: &e" + Main.plugin.getConfig().getString("UnicodeEmojis")));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aBlockedWords&7: &c" + Arrays.toString(Objects.requireNonNull(Main.plugin.getConfig().getString("words")).split(";"))));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aGlobalChat&7: &e" + Main.plugin.getConfig().getString("GlobalChat.Enabled")));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aLocalChat&7: &e" + Main.plugin.getConfig().getString("LocalChat.Enabled")));

                    } else if (args[0].equalsIgnoreCase("reload")) {
                        Main.plugin.reloadConfig();
                        Main.plugin.saveDefaultConfig();
                        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                            Bukkit.getConsoleSender().sendMessage("[WOWCHAT] Register PlaceHolderAPI to WoWChat");
                        } else {
                            Bukkit.getConsoleSender().sendMessage("[WOWCHAT] Could not find PlaceholderAPI! This plugin is required if you want PLACEHOLDERS.");
                        }
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("ReloadSuccess"))));
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Error") + Main.plugin.getConfig().getString("Use")));
                    }
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Error")+ Main.plugin.getConfig().getString("Use")));
                }
            }
        }
        return false;
    }



    @EventHandler(
            priority = EventPriority.HIGH
    )
    public void onChat(AsyncPlayerChatEvent e){
            Player p = e.getPlayer();
            if (!p.hasPermission("wowchat.bypass") && !chat) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Error") + Main.plugin.getConfig().getString("YouCantChat")));
                e.setCancelled(true);
            }
    }
}

