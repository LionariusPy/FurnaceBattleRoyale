package com.lionarius.FBR.tasks;

import com.lionarius.FBR.FurnaceBattleRoyale;
import com.lionarius.FBR.config.ConfigManager;
import com.lionarius.FBR.events.FurnaceBurnedOutEvent;
import com.lionarius.FBR.player.FBRFurnace;
import org.bukkit.block.Furnace;
import org.bukkit.scheduler.BukkitRunnable;

public class FBRFurnaceTask extends BukkitRunnable {

    private static final int maxTimeWithoutSmelting = ConfigManager.FURNACE_TIME_WITHOUT_SMELTING * (int) (20L / ConfigManager.TASK_UPDATE_TIME);

    private final FBRFurnace furnace;
    private int timeWithoutSmelting = maxTimeWithoutSmelting;

    public FBRFurnaceTask(FBRFurnace furnace) {
        this.furnace = furnace;

        this.runTaskTimer(FurnaceBattleRoyale.getInstance(), 0L, ConfigManager.TASK_UPDATE_TIME);
    }

    public int getFurnaceStatus() {
        int status = 0;

        if (timeWithoutSmelting <= maxTimeWithoutSmelting / 2 || (furnace.getBurnTime() <= maxTimeWithoutSmelting / 2 * 20 && furnace.getFurnaceState().getInventory().getFuel() == null))
            status = 1;
        if (timeWithoutSmelting <= maxTimeWithoutSmelting / 4 || (furnace.getBurnTime() <= maxTimeWithoutSmelting / 4 * 20 && furnace.getFurnaceState().getInventory().getFuel() == null))
            status = 2;

        return status;
    }

    @Override
    public void run() {
        if (furnace.getFurnaceBlock().getType() != ConfigManager.FURNACE_TYPE || timeWithoutSmelting <= 0 || furnace.getBurnTime() <= 0 || furnace.getTeam().getAliveMembers().size() == 0) {
            FurnaceBattleRoyale.getInstance().getServer().getPluginManager().callEvent(new FurnaceBurnedOutEvent(furnace));

            this.cancel();
            return;
        }
        if (furnace.getFurnaceState().getInventory().getSmelting() == null || furnace.getCookTime() <= 0)
            timeWithoutSmelting--;
        else {
            timeWithoutSmelting = maxTimeWithoutSmelting;

            Furnace state = furnace.getFurnaceState();
            state.setCookTime((short) (state.getCookTime() + ConfigManager.TASK_UPDATE_TIME * 2));
            if (state.getCookTime() > 199) state.setCookTime((short) 199);
            state.update();
        }
    }
}
