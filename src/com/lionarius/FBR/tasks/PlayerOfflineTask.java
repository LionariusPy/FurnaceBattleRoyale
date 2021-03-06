package com.lionarius.FBR.tasks;

import com.lionarius.FBR.FurnaceBattleRoyale;
import com.lionarius.FBR.config.ConfigManager;
import com.lionarius.FBR.player.FBRPlayer;
import com.lionarius.FBR.player.PlayerState;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerOfflineTask extends BukkitRunnable {

//    private static final int maxOfflineTime = ConfigManager.MAX_OFFLINE_TIME;

    private final FBRPlayer fbrPlayer;
    private int currentOfflineTime = ConfigManager.MAX_OFFLINE_TIME;

    public PlayerOfflineTask(FBRPlayer fbrPlayer) {
        this.fbrPlayer = fbrPlayer;

        this.runTaskTimer(FurnaceBattleRoyale.getInstance(), 0L, 20L);
    }

    @Override
    public void run() {
        if(currentOfflineTime <= 0)
        {
            fbrPlayer.setPlayerState(PlayerState.DEAD);
            this.cancel();
        }
        currentOfflineTime--;
    }
}
