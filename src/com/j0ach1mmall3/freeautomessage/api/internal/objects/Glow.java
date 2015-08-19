package com.j0ach1mmall3.freeautomessage.api.internal.objects;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

/**
 * Created by j0ach1mmall3 on 17:20 18/08/2015 using IntelliJ IDEA.
 */
public class Glow extends Enchantment{
    private Enchantment glow;
    private String name;
    public Glow() {
        super(255);
    }
    @Override
    public boolean canEnchantItem(ItemStack arg0) {
        return true;
    }
    @Override
    public boolean conflictsWith(Enchantment arg0) {
        return false;
    }
    @Override
    public EnchantmentTarget getItemTarget() {
        return null;
    }
    @Override
    public int getMaxLevel() {
        return 10;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public int getStartLevel() {
        return 1;
    }
    @SuppressWarnings("deprecation")
    public Enchantment getGlow()	{
        if ( glow != null )
            return glow;
        try	{
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null , true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        glow = new Glow();
        try{
            Enchantment.registerEnchantment(glow);
        }catch (Exception e){
            //Enchantment already registered
        }
        return glow;
    }
}
