package com.lionarius.FBR.player;

import org.bukkit.entity.Player;
import com.lionarius.FBR.config.ConfigManager;
import com.lionarius.FBR.game.GameManager;
import com.lionarius.FBR.game.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerManager
{
    private static final List<FBRPlayer> players = new ArrayList<FBRPlayer>();

    public static List<FBRPlayer> getPlayersList() { return players; }

    public static FBRPlayer newFBRPlayer(Player player)
    {
        FBRPlayer fbrPlayer = new FBRPlayer(player);
        players.add(fbrPlayer);
        return fbrPlayer;
    }

    public static boolean doesPlayerExists(Player player)
    {
        if(players.size() == 0) return false;
        return (getFBRPlayer(player) != null);
    }

    public static FBRPlayer getFBRPlayer(Player player)
    {
        return getFBRPlayer(player.getUniqueId());
    }

    public static FBRPlayer getFBRPlayer(UUID uuid)
    {
        for(FBRPlayer fbrPlayer : getPlayersList())
        {
            if(fbrPlayer.getUuid().equals(uuid)) return fbrPlayer;
        }
        return null;
    }

    public static FBRPlayer getFBRPlayer(String name)
    {
        for(FBRPlayer fbrPlayer : getPlayersList())
        {
            if(fbrPlayer.getName().equalsIgnoreCase(name)) return fbrPlayer;
        }
        return null;
    }

    public static FBRPlayer newOrGetFBRPlayer(Player player)
    {
        FBRPlayer fbrPlayer;

        if(!doesPlayerExists(player)) fbrPlayer = newFBRPlayer(player);
        else fbrPlayer = getFBRPlayer(player);

        return fbrPlayer;
    }

    public static boolean canJoin(Player player)
    {
        return GameManager.getState() == GameState.WAITING || player.isOp() || ConfigManager.CAN_SPECTATE;
    }

    public static List<FBRPlayer> getAlivePlayers()
    {
        List<FBRPlayer> alivePlayers = new ArrayList<>();

        for(FBRPlayer player : players)
        {
            if(player.getState() == PlayerState.PLAYING) alivePlayers.add(player);
        }

        return alivePlayers;
    }
}
