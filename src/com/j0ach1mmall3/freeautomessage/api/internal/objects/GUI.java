package com.j0ach1mmall3.freeautomessage.api.internal.objects;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by j0ach1mmall3 on 14:05 19/08/2015 using IntelliJ IDEA.
 */
public class GUI {
    private String name;
    private ItemStack[] items;

    public GUI(String name, ItemStack[] items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    private int roundUp(int from, int to) {
        return (from + (to-1)) / to * to;
    }

    public void open(Player p) {
        int size = roundUp(items.length, 9);
        Inventory gui = Bukkit.createInventory(p, size, PlaceholderAPI.setPlaceholders(p, name));
        for(int a=0;a<items.length;a++){
            if(items[a] == null){
                gui.setItem(a, new ItemStack(Material.AIR));
            } else {
                gui.setItem(a, items[a]);
            }
        }
        p.openInventory(gui);
    }

    public Boolean hasClicked(InventoryClickEvent e) {
        if(e.getView().getTopInventory() != null){
            if(e.getView().getTopInventory().getName().equalsIgnoreCase(PlaceholderAPI.setPlaceholders((Player) e.getWhoClicked(), name))){
                if(e.getCurrentItem() != null){
                    if(e.getCurrentItem().getType() != Material.AIR){
                        if(e.getRawSlot() > e.getInventory().getSize()){
                            e.setCancelled(true);
                            return false;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
