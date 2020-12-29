package me.opkarol.main;
import GlobalChat.ChatListener;
import GlobalChat.Global;
import GlobalChat.GlobalChat;
import Others.ChannelSelection;
import Listners.*;
import Komendy.Chat;
import Others.Join;
import Others.Leave;
import Others.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public class Main extends JavaPlugin implements Listener {
    String LastConfigVersion = "Beta 0.5";
    public static Main plugin;
    AntyZleSlowa ZleSlowa;
    Chat chat;
    ChatDelay chatDelay;
    Kropka kropka;
    AntyZleSlowa antyslowa;
    Kolorki kolorki;
    Join join;
    Leave leave;
    Global global;
    ChatListener chatListener;
    GlobalChat globalChat;
    UpdateChecker updateChecker;
    public Main() {
    }
    @Override
    public void onEnable() {
        plugin = this;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.kolorki = new Kolorki(this);
        this.antyslowa = new AntyZleSlowa(this);
        this.kropka = new Kropka(this);
        this.ZleSlowa = new AntyZleSlowa(this);
        this.updateChecker = new UpdateChecker(this);
        this.chat = new Chat(this);
        this.chatDelay = new ChatDelay(this);
        this.join = new Join(this);
        this.leave = new Leave(this);
        this.global = new Global(this);
        this.chatListener = new ChatListener(this);
        this.globalChat = new GlobalChat(this);
        Main.plugin.saveDefaultConfig();
        getCommand("channel").setExecutor(new ChannelSelection(this));
        if (Objects.equals(Main.plugin.getConfig().getString("ConfigVersion"), LastConfigVersion)) {
            Bukkit.getConsoleSender().sendMessage("[WOWCHAT] You have the newest version of config :)");
        } else {
            Bukkit.getConsoleSender().sendMessage("[WOWCHAT] You must download new version of config :(");
        }
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getConsoleSender().sendMessage("[WOWCHAT] Register PlaceHolderAPI to WoWChat");
        } else {
            Bukkit.getConsoleSender().sendMessage("[WOWCHAT] Could not find PlaceholderAPI! This plugin is required if you want PLACEHOLDERS.");
        }

        Logger logger = this.getLogger();

        new UpdateChecker(this, 86246).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info("[WOWCHAT] You have the latest version of this plugin.");
            } else {
                logger.info("[WOWCHAT] There is a new update available.");
                logger.info("[WOWCHAT] New version: " + version);
                logger.info("[WOWCHAT] Your version: "+ this.getDescription().getVersion());
                logger.info("[WOWCHAT] Download it from here:");
                logger.info("[WOWCHAT] https://www.spigotmc.org/resources/86246/");
            }
        });

    }


}
