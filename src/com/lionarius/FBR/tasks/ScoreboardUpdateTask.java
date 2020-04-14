package com.lionarius.FBR.tasks;

import com.lionarius.FBR.FurnaceBattleRoyale;
import com.lionarius.FBR.config.GameConfigManager;
import com.lionarius.FBR.game.GameManager;
import com.lionarius.FBR.game.GameState;
import com.lionarius.FBR.team.FBRTeam;
import com.lionarius.FBR.team.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ScoreboardUpdateTask extends BukkitRunnable {

    public ScoreboardUpdateTask() {
        this.runTaskTimer(FurnaceBattleRoyale.getInstance(), 0L, GameConfigManager.TASK_UPDATE_TIME);
    }

    @Override
    public void run() {

        List<FBRTeam> teams = TeamManager.getFBRTeams();

        if(teams.size() == 0) return;

        for(FBRTeam fbrTeam : teams)
        {
//            int teamID = fbrTeam.getTeamID();

            if(GameManager.getCountdownTask() != null)
                fbrTeam.getScoreboard().getTeam("time"/* + teamID*/).setSuffix(GameManager.getCountdownTask().getFormattedTime());
            fbrTeam.getScoreboard().getTeam("status"/* + teamID*/).setSuffix(fbrTeam.getFurnaceStatus());

//            if(fbrTeam.getMembers().size() == 1) fbrTeam.getScoreboard().getTeam("mode"/* + teamID*/).setSuffix(ChatColor.GOLD.toString() + "ОДИНОЧНЫЙ");
//            else fbrTeam.getScoreboard().getTeam("mode"/* + teamID*/).setSuffix(ChatColor.GOLD.toString() + "КОМАНДНЫЙ");
        }

        if(GameManager.getGameState() == GameState.ENDED) this.cancel();
    }
}
