package com.lionarius.FBR.tasks;

import com.lionarius.FBR.FurnaceBattleRoyale;
import com.lionarius.FBR.config.GameConfigManager;
import com.lionarius.FBR.game.GameManager;
import com.lionarius.FBR.game.GameState;
import com.lionarius.FBR.team.FBRTeam;
import com.lionarius.FBR.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardUpdateTask extends BukkitRunnable {

    public ScoreboardUpdateTask()
    {
        this.runTaskTimer(FurnaceBattleRoyale.getInstance(), 0L, GameConfigManager.TASK_UPDATE_TIME);
    }

    @Override
    public void run() {
//        for(FBRTeam fbrTeam : TeamManager.getFBRTeams())
//        {
////            int teamID = fbrTeam.getTeamID();
////
//            fbrTeam.getScoreboard().getTeam("time_" + teamID).setSuffix(GameManager.getCountdownTask().getFormattedTime());
//            fbrTeam.getScoreboard().getTeam("status_" + teamID).setSuffix(fbrTeam.getFurnaceStatus());
//        }

        for(Player player : Bukkit.getOnlinePlayers())
        {
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective objective = scoreboard.registerNewObjective("hud", "dummy", "DoomsDay");

            // Здесь свитч на стадии игры
            switch (GameManager.getGameState())
            {
                case WAITING:
                    // Добавляем нужную инфу в скорборд
                    objective.getScore("test: " + "test info").setScore(0);
                    break;
                case PLAYING_1:
                    // Добавляем другую нужную инфу в скорборд
                    break;
                // И так далее для каждой фазы
                default: break;
            }

            player.setScoreboard(scoreboard);
        }

        if(GameManager.getGameState() == GameState.ENDED) this.cancel();
    }
}
