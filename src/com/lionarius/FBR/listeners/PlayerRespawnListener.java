package com.lionarius.FBR.listeners;

import com.lionarius.FBR.game.GameManager;
import com.lionarius.FBR.player.FBRPlayer;
import com.lionarius.FBR.player.PlayerManager;
import com.lionarius.FBR.utils.LocationUtils;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event)
    {
        FBRPlayer fbrPlayer = PlayerManager.getFBRPlayer(event.getPlayer());
        Chunk teamChunk = fbrPlayer.getTeam().getTeamChunk();

        fbrPlayer.updateWorldBorder();
        if(GameManager.isChunkPhase()) {
            if (event.getRespawnLocation().getChunk() != teamChunk) {
                event.setRespawnLocation(LocationUtils.getDownBlock(fbrPlayer.getTeam().getTeamChunkLocation()));
            }
        }
    }
}
