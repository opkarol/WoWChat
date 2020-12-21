package me.opkarol.main;
import GlobalChat.ChatListener;
import GlobalChat.Global;
import GlobalChat.GlobalChat;
import Listners.*;
import Komendy.Chat;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

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

    public Main() {
    }
    public String getConfigString(){
        return LastConfigVersion;
    }
    @Override
    public void onEnable() {
        plugin = this;
        this.kolorki = new Kolorki(this);
        this.antyslowa = new AntyZleSlowa(this);
        this.kropka = new Kropka(this);
        this.ZleSlowa = new AntyZleSlowa(this);
        this.chat = new Chat(this);
        this.chatDelay = new ChatDelay(this);
        this.join = new Join(this);
        this.leave = new Leave(this);
        this.global = new Global(this);
        this.chatListener = new ChatListener(this);
        this.globalChat = new GlobalChat(this);

        Main.plugin.saveDefaultConfig();
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
    }
}
