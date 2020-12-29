package Others;

import me.clip.placeholderapi.PlaceholderAPI;
import me.opkarol.main.Main;
import org.apache.logging.log4j.core.net.Priority;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

public class ChannelSelection implements Listener, CommandExecutor {

    Main plugin;
    String perm1 = "wowchat.channel";
    String GlobalJoin = ChatColor.translateAlternateColorCodes('&',Main.plugin.getConfig().getString("ChannelSelection.Messages.SelectedGlobal"));
    String LocalJoin = ChatColor.translateAlternateColorCodes('&',Main.plugin.getConfig().getString("ChannelSelection.Messages.SelectedLocal"));
    String AlreadyOnChannel = ChatColor.translateAlternateColorCodes('&',Main.plugin.getConfig().getString("ChannelSelection.Messages.AlreadyOnChannel").replace("[*ERROR*]",Main.plugin.getConfig().getString("Error")));

    public static HashMap<String, Integer> Channel = new HashMap<String, Integer>();
    //0 - Global
    //1 - Local
    //2 - Player
    //3 - AdminChat

    public ChannelSelection(Main plugin) {
        Main.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    HashMap<String, Integer> LastPlayedChannel = new HashMap<String, Integer>();
    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e){
        LastPlayedChannel.put(e.getPlayer().getDisplayName(), Channel.get(e.getPlayer().getDisplayName()));
    }
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        if(!event.getPlayer().hasPlayedBefore()) {
            Channel.put(event.getPlayer().getDisplayName(), 0);
        }
        if(event.getPlayer().hasPlayedBefore()){
            Channel.put(event.getPlayer().getDisplayName(), LastPlayedChannel.get(event.getPlayer().getDisplayName()));
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if(Channel.get(p.getDisplayName()) == null){
            Channel.put(p.getDisplayName(), 0);
        }
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (Main.plugin.getConfig().getBoolean("ChannelSelection.Enabled") == true) {
            if (Channel.get(p.getDisplayName()) != null) {
                if (sender != null && cmd.getName().equalsIgnoreCase("channel")) {
                    //if (p.hasPermission("wowchat.channel") || p.isOp()) {
                    if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("global")) { //Global channel, 0
                            if (!(Channel.get(p.getDisplayName()) == 0)) {
                                p.sendMessage(GlobalJoin);
                                Channel.put(p.getDisplayName(), 0);
                            } else {
                                p.sendMessage(AlreadyOnChannel);
                            }
                        } else if (args[0].equalsIgnoreCase("local")) { //Local channel, 1
                            if (!(Channel.get(p.getDisplayName()) == 1)) {
                                p.sendMessage(LocalJoin);
                                Channel.put(p.getDisplayName(), 1);

                            } else {
                                p.sendMessage(AlreadyOnChannel);
                            }
                        } else { //Channel Selection USE from Main
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Error") + Main.plugin.getConfig().getString("ChannelUse")));
                        }
                    } else { //Channel Selection USE from Main
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Error") + Main.plugin.getConfig().getString("ChannelUse")));
                    }
                }// else { //Permission, if player hasnt yet
                   // p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Error") + Main.plugin.getConfig().getString("Permission").replace("%permission%", perm1)));
                //}
            } else {
                Channel.put(p.getDisplayName(), 0);
                return true;
            }
            //}
        }   else{
            p.sendMessage("Enable ChannelSelection in config.yml");
        }
        return false;
    }
}


