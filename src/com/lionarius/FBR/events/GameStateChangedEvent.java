package com.lionarius.FBR.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import com.lionarius.FBR.game.GameState;

public class GameStateChangedEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private GameState gameState;

    public GameStateChangedEvent(GameState gameState)
    {
        this.gameState = gameState;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public GameState getGameState() {
        return gameState;
    }
}
