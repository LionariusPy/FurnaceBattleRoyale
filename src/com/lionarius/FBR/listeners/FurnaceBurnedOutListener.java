package com.lionarius.FBR.listeners;

import com.lionarius.FBR.events.FurnaceBurnedOutEvent;
import com.lionarius.FBR.game.GameManager;
import com.lionarius.FBR.game.GameState;
import com.lionarius.FBR.player.FBRPlayer;
import com.lionarius.FBR.player.PlayerState;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FurnaceBurnedOutListener implements Listener {

    @EventHandler
    public void onFurnace(FurnaceBurnedOutEvent event) {
        if (GameManager.getState() != GameState.ENDED) {
            World world = event.getFBRFurnace().getFurnaceBlock().getWorld();

            world.createExplosion(event.getFBRFurnace().getFurnaceBlock().getLocation(), 2, true);

            for (FBRPlayer player : event.getFBRFurnace().getPlayers()) {
                if (player.getState() != PlayerState.DEAD) player.setPlayerState(PlayerState.DEAD);
                player.getPlayer().sendMessage(ChatColor.RED + "Ваша печка была разрушена");
            }
        }
    }
}
