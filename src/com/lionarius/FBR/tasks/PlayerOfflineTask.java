package com.lionarius.FBR.tasks;

import com.lionarius.FBR.FurnaceBattleRoyale;
import com.lionarius.FBR.config.GameConfigManager;
import com.lionarius.FBR.player.FBRPlayer;
import com.lionarius.FBR.player.PlayerState;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerOfflineTask extends BukkitRunnable {

    private static final int maxOfflineTime = GameConfigManager.MAX_OFFLINE_TIME;

    private FurnaceBattleRoyale plugin;
    private FBRPlayer fbrPlayer;
    private int currentOfflineTime = GameConfigManager.MAX_OFFLINE_TIME;

    public PlayerOfflineTask(FurnaceBattleRoyale plugin, FBRPlayer fbrPlayer)
    {
        this.plugin = plugin;
        this.fbrPlayer = fbrPlayer;

        this.runTaskTimer(plugin, 0L, 20L);
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