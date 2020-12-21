package GlobalChat;

import me.opkarol.main.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import java.util.Objects;


public class Global implements Listener, CommandExecutor {
    Main plugin;

    public Global(Main plugin) {
        Objects.requireNonNull(Main.plugin.getCommand("spy")).setExecutor(this);
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        if(Main.plugin.getConfig().getBoolean("GlobalChat.Enabled")) {
            if (cmd.getName().equalsIgnoreCase("spy")) {
                if (player.hasPermission("wowchat.spy") || player.isOp()) {
                    if (ChatListener.bypass.contains(player)) {
                        ChatListener.bypass.remove(player);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("LocalChat.Messages.NoBypassLonger"))));
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("LocalChat.Messages.Bypass"))));
                        ChatListener.bypass.add(player);
                    }
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.plugin.getConfig().getString("Error")) + Objects.requireNonNull(Main.plugin.getConfig().getString("Permission"))));
                }
            }
        }
        return false;
    }
}
