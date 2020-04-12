package com.lionarius.FBR.team;

import com.lionarius.FBR.config.GameConfigManager;
import com.lionarius.FBR.game.GameManager;
import com.lionarius.FBR.game.GameState;
import com.lionarius.FBR.player.FBRFurnace;
import com.lionarius.FBR.player.FBRPlayer;
import com.lionarius.FBR.player.PlayerState;
import com.lionarius.FBR.tasks.CountdownTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class FBRTeam {

    private List<FBRPlayer> members;
    private boolean isReadyToStart;
    private int teamID;
    private Scoreboard scoreboard;
    private FBRFurnace furnace;
    private Chunk teamChunk;

    public FBRTeam(FBRPlayer player)
    {
        members = new ArrayList<FBRPlayer>();
        teamID = TeamManager.GetNewTeamID();

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Team scoreboardTeam = scoreboard.registerNewTeam("team_" + teamID);
        scoreboardTeam.setAllowFriendlyFire(false);
        scoreboardTeam.setColor(ChatColor.GREEN);
        scoreboardTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
        scoreboardTeam.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.FOR_OWN_TEAM);
        scoreboardTeam.setPrefix("[Команда] ");

        Objective scoreboardObjective = scoreboard.registerNewObjective("hud", "dummy", "Doomsday");
        scoreboardObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        scoreboardObjective.getScore(ChatColor.AQUA + "Состав команды: ").setScore(15);

        scoreboardObjective.getScore(ChatColor.BLUE.toString()).setScore(4);

        Team statusTeam = scoreboard.registerNewTeam("status_" + teamID);
        statusTeam.addEntry("Статус печки: ");
        statusTeam.setSuffix(getFurnaceStatus());
        scoreboardObjective.getScore("Статус печки: ").setScore(3);

        scoreboardObjective.getScore(ChatColor.AQUA.toString()).setScore(2);

        Team timeTeam = scoreboard.registerNewTeam("time_" + teamID);
        timeTeam.addEntry(ChatColor.GOLD.toString() + "    ");
        timeTeam.setSuffix("");
        scoreboardObjective.getScore("Время до следующего этапа: ").setScore(1);
        scoreboardObjective.getScore(ChatColor.GOLD.toString() + "    ").setScore(0);

        addPlayer(player);
        player.setLeader(true);
        setReadyToStart(false);

//        scoreboardObjective.get
    }

    public FBRPlayer getLeader()
    {
        for (FBRPlayer player : members)
        {
            if(player.isLeader()) return player;
        }
        return null;
    }

    public int getTeamID()
    {
        return this.teamID;
    }

    public void setFurnace(FBRFurnace furnace)
    {
        this.furnace = furnace;
        for (FBRPlayer player : members)
        {
            player.setFurnace(furnace);
        }
    }

    public void createFurnace(Location location)
    {
        setFurnace(new FBRFurnace(location.getWorld() ,location, members));
    }

    public void addPlayer(FBRPlayer player)
    {
        scoreboard.getTeam("team_" + teamID).addEntry(player.getName());

        if(player.getTeam() != null && player.getTeam() != this) {
            player.getTeam().removePlayer(player, false);
        }

        if(player.isLeader()) player.setLeader(false);


        player.setTeam(this);
        members.add(player);

        Objective objective = scoreboard.getObjective("hud");
        objective.getScore("    " + ChatColor.GREEN + player.getName()).setScore(15 - members.size());

        player.updatePlayerVisuals(scoreboard);
    }

    public String getFurnaceStatus()
    {
        if(furnace == null || furnace.getFurnaceBlock().getType() != GameConfigManager.FURNACE_TYPE || furnace.getUpdateTask().isCancelled()) return ChatColor.RED.toString() + ChatColor.BOLD.toString() + "НЕИЗВЕСТНО";

        switch (furnace.getUpdateTask().getFurnaceStatus())
        {
            case 0: return ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "ОТЛИЧНО";
            case 1: return ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "ВНИМАНИЕ";
            case 2: return ChatColor.RED.toString() + ChatColor.BOLD.toString() + "ОПАСНО";
            default: return ChatColor.RED.toString() + ChatColor.BOLD.toString() + "НЕИЗВЕСТНО";
        }
    }

    public void removePlayer(FBRPlayer player, boolean createNewTeam)
    {
        if(!members.contains(player)) return;

        if(scoreboard.getTeam("team_" + teamID).hasEntry(player.getName()))
            scoreboard.getTeam("team_" + teamID).removeEntry(player.getName());
        members.remove(player);

        if(createNewTeam)
            player.setTeam(new FBRTeam(player));

        if(members.size() != 0 && getLeader() == null)
        {

            members.get(0).setLeader(true);
        }
        Objective objective = scoreboard.getObjective("hud");
        objective.getScoreboard().resetScores("    " + ChatColor.GREEN + player.getName());
    }


    public void updateTeamVisuals()
    {
        for(FBRPlayer fbrPlayer : members)
        {
            fbrPlayer.updatePlayerVisuals(scoreboard);
        }
    }

    public boolean isReadyToStart()
    {
        return isReadyToStart;
    }

    public void setReadyToStart(boolean readyToStart)
    {
        isReadyToStart = readyToStart;

        if(TeamManager.isReadyPercent(0.5f))
        {
            if(GameManager.getCountdownTask() == null) {
                CountdownTask.ExecutableCountdownAction update = (countdown) ->
                {
                    if (countdown.getTimeInSeconds() % 5 == 0 || countdown.getTimeInSeconds() < 5)
                        Bukkit.getServer().broadcastMessage(ChatColor.RED + "Игра начнется через " + countdown.getFormattedTime());
                };

                CountdownTask.ExecutableCountdownAction end = (countdown) ->
                {
                    if (countdown.getTimeInSeconds() > 0)
                        Bukkit.getServer().broadcastMessage(ChatColor.RED + "Начало игры отменено");
                    else {
                        Bukkit.getServer().broadcastMessage(ChatColor.RED + "Игра начинается...");
                        GameManager.setGameState(GameState.PLAYING_1);
                    }
                };

                GameManager.createCountdownTask(10, update, end);
            }
        }
        else
        {
            GameManager.stopCountdownTask();
        }
    }

    public void setScoreboard(Scoreboard scoreboard)
    {
        this.scoreboard = scoreboard;
        updateTeamVisuals();
    }

    public void playerDied(FBRPlayer fbrPlayer)
    {
        Objective objective = scoreboard.getObjective("hud");
        int score = objective.getScore("    " + ChatColor.GREEN + fbrPlayer.getName()).getScore();
        objective.getScoreboard().resetScores("    " + ChatColor.GREEN + fbrPlayer.getName());

        objective.getScore("    " + ChatColor.GRAY + fbrPlayer.getName()).setScore(score);
    }

    public Scoreboard getScoreboard()
    {
        return scoreboard;
    }

    public Chunk getTeamChunk() {
        return teamChunk;
    }

    public Location getTeamChunkLocation()
    {
        return new Location(teamChunk.getWorld(), teamChunk.getX() * 16 + 8, 255, teamChunk.getZ() * 16 + 8);
    }

    public void setTeamChunk(Chunk teamChunk) {
        this.teamChunk = teamChunk;
    }

    public List<FBRPlayer> getMembers()
    {
        return members;
    }

    public List<FBRPlayer> getAliveMembers()
    {
        List<FBRPlayer> aliveFbrPlayers = new ArrayList<FBRPlayer>();

        for(FBRPlayer member : members)
        {
            if(member.getState() != PlayerState.DEAD) aliveFbrPlayers.add(member);
        }
        return aliveFbrPlayers;
    }
}
