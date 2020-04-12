package com.lionarius.FBR.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import com.lionarius.FBR.game.GameState;

public class GameStateChangedEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private GameState gameState;
    private GameState oldGameState;

    public GameStateChangedEvent(GameState oldGameState, GameState gameState)
    {
        this.gameState = gameState;
        this.oldGameState = oldGameState;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList()
    {
        return HANDLERS_LIST;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getOldGameState() { return oldGameState; }
}
