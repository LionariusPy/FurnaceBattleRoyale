package com.lionarius.FBR.tasks;

import com.lionarius.FBR.FurnaceBattleRoyale;
import com.lionarius.FBR.utils.TimeUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownTask extends BukkitRunnable {

    private long time;
    private final ExecutableCountdownAction updateAction;
    private final ExecutableCountdownAction stopAction;

    public CountdownTask(long seconds, ExecutableCountdownAction updateAction, ExecutableCountdownAction stopAction) {
        this.time = seconds;
        this.updateAction = updateAction;
        this.stopAction = stopAction;

        this.runTaskTimer(FurnaceBattleRoyale.getInstance(), 0L, 20L);
    }

    public CountdownTask(long seconds, ExecutableCountdownAction updateAction) {
        this(seconds, updateAction, null);
    }

    @Override
    public void run() {
        if (time <= 0) {
            stopCountdown();
            return;
        }
        updateAction.execute(this);
        time--;
    }

    public long getTimeInSeconds()
    {
        return time;
    }

    public String getFormattedTime() {
        return TimeUtils.formatTime(time);
    }

    public void stopCountdown() {
        if(stopAction != null) stopAction.execute(this);
        this.cancel();
    }

    public void stopCountdownNoEnd() {
        this.cancel();
    }

    public interface ExecutableCountdownAction {
        void execute(CountdownTask countdown);
    }
}
