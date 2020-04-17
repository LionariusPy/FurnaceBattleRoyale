package com.lionarius.FBR.events;

import com.lionarius.FBR.player.FBRFurnace;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FurnaceBurnedOutEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final FBRFurnace furnace;

    public FurnaceBurnedOutEvent(FBRFurnace furnace) {
        this.furnace = furnace;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public FBRFurnace getFBRFurnace() {
        return furnace;
    }
}
