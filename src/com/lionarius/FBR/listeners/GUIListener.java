package com.lionarius.FBR.listeners;

import com.lionarius.FBR.game.GameManager;
import com.lionarius.FBR.game.GameState;
import com.lionarius.FBR.gui.AbstractGUI;
import com.lionarius.FBR.gui.MenuGUI;
import com.lionarius.FBR.player.FBRPlayer;
import com.lionarius.FBR.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class GUIListener implements Listener {

    public static GUIListener instance;

    public GUIListener() {
        instance = this;
    }

    public static GUIListener getInstance() {
        return instance;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        FBRPlayer fbrPlayer = PlayerManager.getFBRPlayer(event.getWhoClicked().getUniqueId());
        AbstractGUI currentGUI = fbrPlayer.getCurrentGUI();

        if (currentGUI != null) {
            if (event.getCurrentItem() != null && event.getClickedInventory() == currentGUI.getInventory()) {
                InventoryAction currentAction = event.getAction();

                int slot = event.getSlot();
                currentGUI.performAction(slot, fbrPlayer, currentAction);
            }
            event.setCancelled(true);
        }

        if (GameManager.getState() == GameState.WAITING) event.setCancelled(true);
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        if (GameManager.getState() == GameState.WAITING) event.setCancelled(true);
    }

    @EventHandler
    public void onItemSwap(PlayerSwapHandItemsEvent event) {
        if (GameManager.getState() == GameState.WAITING) event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        FBRPlayer fbrPlayer = PlayerManager.getFBRPlayer(event.getPlayer().getUniqueId());

        if (fbrPlayer != null && fbrPlayer.getCurrentGUI() != null && event.getInventory() == fbrPlayer.getCurrentGUI().getInventory()) {
            fbrPlayer.closeInventory();
        }
    }

    @EventHandler
    public void onItemClick(PlayerInteractEvent event) {
        if (GameManager.getState() == GameState.WAITING) {
            if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem() != null) {
                FBRPlayer fbrPlayer = PlayerManager.getFBRPlayer(event.getPlayer());

                if (event.getItem().getType() == Material.COMPASS) {
                    new MenuGUI(fbrPlayer);
                }
            }
        }
    }
}
