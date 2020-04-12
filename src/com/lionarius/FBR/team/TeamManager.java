package com.lionarius.FBR.team;

import com.lionarius.FBR.player.FBRPlayer;
import com.lionarius.FBR.player.PlayerManager;
import com.lionarius.FBR.player.PlayerState;

import java.util.ArrayList;
import java.util.List;

public class TeamManager {

    private static int lastTeamID = 0;

    public static int GetNewTeamID()
    {
        lastTeamID++;
        return lastTeamID;
    }

    public static List<FBRTeam> getFBRTeams()
    {
        List<FBRTeam> teams = new ArrayList<FBRTeam>();
        for(FBRPlayer fbrPlayer : PlayerManager.getPlayersList())
        {
            FBRTeam team = fbrPlayer.getTeam();
            if(!teams.contains(team)) teams.add(team);
        }
        return teams;
    }

    public static List<FBRTeam> getAliveFBRTeams()
    {
        List<FBRTeam> teams = new ArrayList<FBRTeam>();
        for(FBRPlayer fbrPlayer : PlayerManager.getPlayersList())
        {
            FBRTeam team = fbrPlayer.getTeam();
            if(!teams.contains(team) && fbrPlayer.getState() != PlayerState.DEAD) teams.add(team);
        }
        return teams;
    }

    public static boolean isReadyPercent(float percent)
    {
        List<FBRTeam> teams = getFBRTeams();

        if(teams.size() == 0) return false;

        float count = teams.size();
        float ready = 0;

        for(FBRTeam fbrTeam : teams)
        {
            if(fbrTeam.isReadyToStart()) ready++;
        }

        return (ready / count) >= percent;
    }
}
