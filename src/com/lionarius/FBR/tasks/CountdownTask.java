package com.lionarius.FBR.tasks;

import com.lionarius.FBR.utils.TimeUtils;
import org.bukkit.scheduler.BukkitRunnable;
import com.lionarius.FBR.FurnaceBattleRoyale;

public class CountdownTask extends BukkitRunnable {

    private long time;
    private FurnaceBattleRoyale plugin;
    private ExecutableCountdownAction updateAction;
    private ExecutableCountdownAction stopAction;

    public CountdownTask(FurnaceBattleRoyale plugin, long seconds, ExecutableCountdownAction updateAction, ExecutableCountdownAction stopAction)
    {
        this.plugin = plugin;
        this.time = seconds;
        this.updateAction = updateAction;
        this.stopAction = stopAction;

        this.runTaskTimer(plugin, 0L, 20L);
    }

    public CountdownTask(FurnaceBattleRoyale plugin, long seconds, ExecutableCountdownAction updateAction)
    {
        this.plugin = plugin;
        this.time = seconds;
        this.updateAction = updateAction;

        this.runTaskTimer(plugin, 0L, 20L);
    }

    @Override
    public void run() {
        if(time <= 0)
        {
            stopCountdown();
            return;
        }
        updateAction.execute(this);
        time--;
    }

    public long getTimeInSeconds() { return time; }

    public String getFormattedTime()
    {
        return TimeUtils.getFormattedTime(time);
    }

    public void stopCountdown()
    {
        if(stopAction != null) stopAction.execute(this);
        this.cancel();
    }

    public void stopCountdownNoEnd()
    {
        this.cancel();
    }

    public interface ExecutableCountdownAction
    {
        void execute(CountdownTask countdown);
    }
}
