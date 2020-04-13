package com.lionarius.FBR.events;

import com.lionarius.FBR.player.FBRPlayer;
import com.lionarius.FBR.player.PlayerState;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerStateChangedEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private FBRPlayer fbrPlayer;
    private PlayerState playerState;

    public PlayerStateChangedEvent(FBRPlayer fbrPlayer, PlayerState playerState)
    {
        this.fbrPlayer = fbrPlayer;
        this.playerState = playerState;
    }

    @Override
    public HandlerList getHandlers()
    {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList()
    {
        return HANDLERS_LIST;
    }

    public FBRPlayer getFBRPlayer()
    {
        return fbrPlayer;
    }

    public PlayerState getPlayerState()
    {
        return playerState;
    }
}
