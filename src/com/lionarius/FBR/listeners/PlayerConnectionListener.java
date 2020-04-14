package com.lionarius.FBR.listeners;

import com.lionarius.FBR.game.GameManager;
import com.lionarius.FBR.game.GameState;
import com.lionarius.FBR.player.PlayerState;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import com.lionarius.FBR.player.FBRPlayer;
import com.lionarius.FBR.player.PlayerManager;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event)
    {
        Player player = event.getPlayer();

        if(!PlayerManager.canJoin(player))
        {
            event.setKickMessage("Вы не можете присоединиться во время игры");
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
        if(PlayerManager.getFBRPlayer(player.getUniqueId()) == null && GameManager.getState() != GameState.WAITING)
        {
            event.setKickMessage("Вы не можете присоединиться к игре");
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        FBRPlayer fbrPlayer = PlayerManager.newOrGetFBRPlayer(player);
        fbrPlayer.updatePlayer();

        if(GameManager.getState() == GameState.WAITING) event.setJoinMessage(ChatColor.GOLD.toString() + "[DoomsDay] Игрок " + ChatColor.AQUA.toString() + player.getName() + ChatColor.GOLD.toString() + " присоединяется к лобби");
        else if(fbrPlayer.getState() == PlayerState.DEAD) event.setJoinMessage("");


        if(fbrPlayer.getState() == PlayerState.DEAD && player.getGameMode() != GameMode.SPECTATOR)
        {
            player.getInventory().clear();
            player.setGameMode(GameMode.SPECTATOR);

//            if(TeamManager.getAliveFBRTeams().size() == 1)
//                GameManager.setGameState(GameState.ENDED);
        }

        if(GameManager.getState() != GameState.WAITING && fbrPlayer.getState() == PlayerState.WAITING)
        {
            fbrPlayer.setPlayerState(PlayerState.DEAD);
            return;
        }

        if(fbrPlayer.getState() == PlayerState.PLAYING)
        {
            fbrPlayer.playerJoinedBack();
        }

        fbrPlayer.updateWorldBorder();
        fbrPlayer.setPlayerScoreboard(fbrPlayer.getTeam().getScoreboard());
    }

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event)
    {
        FBRPlayer fbrPlayer = PlayerManager.getFBRPlayer(event.getPlayer());

        if(fbrPlayer.getState() == PlayerState.PLAYING && GameManager.getState() != GameState.ENDED)
        {
            fbrPlayer.playerLeftMidgame();
        }

        if(fbrPlayer.getState() == PlayerState.DEAD) event.setQuitMessage("");
    }
}
