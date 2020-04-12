package com.lionarius.FBR.listeners;

import com.lionarius.FBR.game.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class EnterPortalListener implements Listener {

    @EventHandler
    public void onPortalEnter(PlayerPortalEvent event)
    {
        if(!GameManager.isPortalsAllowed()) {
            event.getPlayer().sendMessage("Порталы отключены на данном этапе игры");
            event.setCancelled(true);
        }
    }
}
