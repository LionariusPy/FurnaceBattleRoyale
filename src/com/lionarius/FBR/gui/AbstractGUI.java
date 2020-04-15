package com.lionarius.FBR.gui;

import com.lionarius.FBR.player.FBRPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractGUI {

    private final Inventory inventory;
    private final Map<Integer, ExecutableGUIAction> actionMap = new HashMap<Integer, ExecutableGUIAction>();

    public AbstractGUI(String title, int slots)
    {
        this.inventory = Bukkit.createInventory(null, slots, title);
    }

    public Inventory getInventory()
    {
        return inventory;
    }

    public void setItem(ItemStack item, int slot, ExecutableGUIAction action)
    {
        inventory.setItem(slot, item);
        actionMap.put(slot, action);
    }

    public void performAction(int slot, FBRPlayer player, InventoryAction action)
    {
        actionMap.get(slot).execute(player, action, slot);
    }

    public interface ExecutableGUIAction
    {
        void execute(FBRPlayer player, InventoryAction action, int slot);
    }
}
