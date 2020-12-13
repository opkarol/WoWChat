package me.opkarol.main;
import JoinLeave.Join;
import Komendy.Chat;
import Listners.AntyZleSlowa;
import Listners.ChatDelay;
import Listners.Kolorki;
import Listners.Kropka;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    public static Main plugin;
    public FileConfiguration getConfig = getConfig();
    AntyZleSlowa ZleSlowa;
    Chat chat;
    ChatDelay chatDelay;
    Kropka kropka;
    AntyZleSlowa antyslowa;
    Kolorki kolorki;
    Join join;

    public Main() {
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
        saveDefaultConfig();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            this.getLogger().warning("Register PlaceHolderAPI to WoWChat");
            Bukkit.getPluginManager().registerEvents(this, this);
        } else {
            this.getLogger().warning("Could not find PlaceholderAPI! This plugin is required if you want PLACEHOLDERS.");
        }

    }
}
