package GUIs;

import me.opkarol.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Objects;

public class Chat implements Listener {
    Main plugin;

    public Chat(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    public void onEnable(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static Inventory openGUI(){
        Inventory gui = Bukkit.createInventory(null, 27, "GUI");
        ItemStack kobel = new ItemStack(Material.DIRT, 13);
        ItemMeta meta = kobel.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName("stone");
        meta.setLore(Arrays.asList("glowstone", "dirtek"));
        kobel.setItemMeta(meta);
        gui.setItem(8, kobel);
        return gui;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked(); // The player that clicked the item
        ItemStack clicked = event.getCurrentItem(); // The item that was clicked
        if (event.getView().getTitle().equals("GUI")) { // The inventory is our custom Inventory
            assert clicked != null;
            if (clicked.getType() == Material.DIRT) { // The item that the player clicked it dirt
                event.setCancelled(true); // Make it so the dirt is back in its original spot
                player.closeInventory(); // Closes there inventory
                player.getInventory().addItem(new ItemStack(Material.DIRT, 1)); // Adds dirt
            }
        }
    }
    @EventHandler
    public void onInventoryClick2(InventoryClickEvent event)  {
        if(event.getView().getTitle().equals("GUI")){
            Bukkit.broadcastMessage("DUTEk");
        }
    }
}
