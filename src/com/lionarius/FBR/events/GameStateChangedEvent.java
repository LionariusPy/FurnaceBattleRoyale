package com.lionarius.FBR.events;

import com.lionarius.FBR.game.GameState;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStateChangedEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final GameState gameState;

    public GameStateChangedEvent(GameState gameState) {
        this.gameState = gameState;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public GameState getGameState() {
        return gameState;
    }
}
