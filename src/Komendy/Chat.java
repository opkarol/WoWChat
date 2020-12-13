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

import java.util.Objects;

public class Chat implements Listener, CommandExecutor {
    private static boolean chat = true;
    Main plugin;
    public String bold = ChatColor.BOLD + "";
    public String perm1 = "wow.chat.admin";
    public Chat(Main plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("chat").setExecutor(this);
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;
        if (sender instanceof Player && cmd.getName().equalsIgnoreCase("chat")) {
            if (!p.hasPermission("wow.chat.admin") && !p.isOp()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Error") + " " + Main.plugin.getConfig().getString("Permission").replace("%permission%", perm1)));
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("clear")) {
                    for (int i = 0; i < 101; ++i) {
                        p.sendMessage(" ");
                    }
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("ChatClear.Line1"))));
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("ChatClear.Line2"))));
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("ChatClear.Line3"))));
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("ChatClear.Line4"))));
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("ChatClear.Line5"))));
                }
                else if (args[0].equalsIgnoreCase("on")) {
                    if (!chat) {
                        chat = true;
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("ChatTurnOn"))));
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("GlobalChatTurnOn").replace("%playerchat%", p.getName())));
                        return true;
                    }

                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Error") + " " + Main.plugin.getConfig().getString("ChatOn")));
                    return false;
                }
                else if (args[0].equalsIgnoreCase("off")) {
                    if (chat) {
                        chat = false;
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("ChatTurnOff"))));
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("GlobalChatTurnOff").replace("%playerchat%", p.getName())));
                        return false;
                    }

                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Error") + " " + Main.plugin.getConfig().getString("ChatOff")));
                    return true;
                }
                else if (args[0].equalsIgnoreCase("help")) {
                    p.sendMessage(ChatColor.RED + "Help: --> (In Progress)");
                }
                else if (args[0].equalsIgnoreCase("reload")) {
                    Main.plugin.reloadConfig();
                    Main.plugin.saveDefaultConfig();
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("ReloadSuccess"))));
                }else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Error") + " " + Main.plugin.getConfig().getString("Use")));
                }


            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Error") + " " + Main.plugin.getConfig().getString("Use")));
            }
        }
        return false;
    }



    @EventHandler(
            priority = EventPriority.HIGH
    )
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("wow.chat.bypass") && !chat) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Error") + " " +  Main.plugin.getConfig().getString("YouCantChat")));
            e.setCancelled(true);
        }

    }
}

