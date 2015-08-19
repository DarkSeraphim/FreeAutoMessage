package com.j0ach1mmall3.freeautomessage.api.internal.objects;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j0ach1mmall3 on 17:18 18/08/2015 using IntelliJ IDEA.
 */
public class CustomItem extends ItemStack {
    public CustomItem(Material material, int amount, int durability, String name, List<String> lore, Enchantment e){
        super(new ItemStack(material, amount));
        ItemMeta im = getItemMeta();
        im.setDisplayName(name);
        List<String> lines = new ArrayList<>();
        for(String line : lore){
            if(!line.equalsIgnoreCase("")) lines.add(line);
        }
        im.setLore(lines);
        setItemMeta(im);
        setDurability((short)durability);
        addEnchantment(e, 1);
    }

    public CustomItem(Material material, int amount, int durability, String name, String[] lore, Enchantment e){
        super(new ItemStack(material, amount));
        ItemMeta im = getItemMeta();
        im.setDisplayName(name);
        List<String> lines = new ArrayList<>();
        for(String line : lore){
            if(!line.equalsIgnoreCase("")) lines.add(line);
        }
        im.setLore(lines);
        setItemMeta(im);
        setDurability((short)durability);
        addEnchantment(e, 1);
    }

    public CustomItem(Material material, int amount, int durability, String name, String lore, Enchantment e){
        super(new ItemStack(material, amount));
        ItemMeta im = getItemMeta();
        im.setDisplayName(name);
        if(!lore.equalsIgnoreCase("")){
            List<String> lines = new ArrayList<>();
            for(String line : lore.split("\\|")){
                lines.add(line);
            }
            im.setLore(lines);
        }
        setItemMeta(im);
        setDurability((short)durability);
        addEnchantment(e, 1);
    }

    public CustomItem(Material material, int amount, int durability, String name, List<String> lore){
        super(new ItemStack(material, amount));
        ItemMeta im = getItemMeta();
        im.setDisplayName(name);
        List<String> lines = new ArrayList<>();
        for(String line : lore){
            if(!line.equalsIgnoreCase("")) lines.add(line);
        }
        im.setLore(lines);
        setItemMeta(im);
        setDurability((short)durability);
    }

    public CustomItem(Material material, int amount, int durability, String name, String[] lore){
        super(new ItemStack(material, amount));
        ItemMeta im = getItemMeta();
        im.setDisplayName(name);
        List<String> lines = new ArrayList<>();
        for(String line : lore){
            if(!line.equalsIgnoreCase("")) lines.add(line);
        }
        im.setLore(lines);
        setItemMeta(im);
        setDurability((short)durability);
    }

    public CustomItem(Material material, int amount, int durability, String name, String lore){
        super(new ItemStack(material, amount));
        ItemMeta im = getItemMeta();
        im.setDisplayName(name);
        if(!lore.equalsIgnoreCase("")){
            List<String> lines = new ArrayList<>();
            for(String line : lore.split("\\|")){
                lines.add(line);
            }
            im.setLore(lines);
        }
        setItemMeta(im);
        setDurability((short)durability);
    }

    public CustomItem(Material material, int amount, int durability, String name){
        super(new ItemStack(material, amount));
        ItemMeta im = getItemMeta();
        im.setDisplayName(name);
        setItemMeta(im);
        setDurability((short)durability);
    }

    public CustomItem(Material material, int amount, int durability){
        super(new ItemStack(material, amount));
        setDurability((short)durability);
    }

    public CustomItem(Material material, int amount) {
        super(new ItemStack(material, amount));
    }

    public CustomItem(ItemStack is) {
        super(is);
    }

    public void setName(String name){
        ItemMeta im = getItemMeta();
        im.setDisplayName(name);
        setItemMeta(im);
    }

    public void setLore(List<String> lore){
        ItemMeta im = getItemMeta();
        List<String> lines = new ArrayList<>();
        for(String line : lore){
            if(!line.equalsIgnoreCase("")) lines.add(line);
        }
        im.setLore(lines);
        setItemMeta(im);
    }

    public void setLore(String[] lore){
        ItemMeta im = getItemMeta();
        List<String> lines = new ArrayList<>();
        for(String line : lore){
            if(!line.equalsIgnoreCase("")) lines.add(line);
        }
        im.setLore(lines);
        setItemMeta(im);
    }

    public void setLore(String lore){
        if(!lore.equalsIgnoreCase("")) setLore(lore.split("\\|"));
    }
}
