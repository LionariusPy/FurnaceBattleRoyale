package com.lionarius.FBR.game;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import com.lionarius.FBR.FurnaceBattleRoyale;
import com.lionarius.FBR.events.GameStateChangedEvent;
import com.lionarius.FBR.tasks.CountdownTask;

public class GameManager {

    private static GameState gameState;

    private static CountdownTask countdownTask;

    private static boolean isPortalsAllowed = false;

    public static void setGameState(GameState gameState)
    {
        Validate.notNull(gameState);

        if(GameManager.gameState == gameState) return;

        GameState oldGameState = GameManager.gameState;
        GameManager.gameState = gameState;

        Bukkit.getPluginManager().callEvent(new GameStateChangedEvent(oldGameState, gameState));
    }

    public static GameState getGameState() { return gameState; }

    public static CountdownTask getCountdownTask()
    {
        return countdownTask;
    }

    public static void createCountdownTask(long time, CountdownTask.ExecutableCountdownAction update)
    {
        if(countdownTask != null && !countdownTask.isCancelled()) { stopCountdownTaskNoEnd(); }

        countdownTask = new CountdownTask(FurnaceBattleRoyale.getInstance(), time, update);
    }

    public static void createCountdownTask(long time, CountdownTask.ExecutableCountdownAction update, CountdownTask.ExecutableCountdownAction end)
    {
        if(countdownTask != null && !countdownTask.isCancelled()) { stopCountdownTaskNoEnd(); }

        countdownTask = new CountdownTask(FurnaceBattleRoyale.getInstance(), time, update, end);
    }

    public static void stopCountdownTaskNoEnd()
    {
        if(countdownTask != null) {
            countdownTask.stopCountdownNoEnd();
            countdownTask = null;
        }
    }

    public static void stopCountdownTask()
    {
        if(countdownTask != null) {
            countdownTask.stopCountdown();
            countdownTask = null;
        }
    }

    public static boolean isPVPAllowed()
    {
        if(gameState == GameState.WAITING || gameState == GameState.ENDED || gameState == GameState.PLAYING_1) return false;

        return true;
    }

    public static boolean isPortalsAllowed() {
        return isPortalsAllowed;
    }

    public static void setPortalsAllowed(boolean isPortalsAllowed) {
        GameManager.isPortalsAllowed = isPortalsAllowed;
    }

    public static boolean isChunkPhase()
    {
        return (gameState != GameState.WAITING && gameState != GameState.ENDED && gameState != GameState.PLAYING_1 && gameState != GameState.PLAYING_3);
    }
}
