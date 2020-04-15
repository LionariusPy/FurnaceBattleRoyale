package com.lionarius.FBR.listeners;

import com.lionarius.FBR.FurnaceBattleRoyale;
import com.lionarius.FBR.config.ConfigManager;
import com.lionarius.FBR.game.GameManager;
import com.lionarius.FBR.game.GameState;
import com.lionarius.FBR.player.*;
import com.lionarius.FBR.tasks.CountdownTask;
import com.lionarius.FBR.tasks.ScoreboardUpdateTask;
import com.lionarius.FBR.team.FBRTeam;
import com.lionarius.FBR.team.TeamManager;
import com.lionarius.FBR.utils.LocationUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import com.lionarius.FBR.events.GameStateChangedEvent;

import java.util.Random;

public class GameStateChangedListener implements Listener {

    @EventHandler
    public void onStateChanged(GameStateChangedEvent event)
    {
        switch (event.getGameState()){

            case WAITING:
                new ScoreboardUpdateTask();

                FurnaceBattleRoyale.getWorld().getWorldBorder().setCenter(0,0);
                FurnaceBattleRoyale.getWorld().getWorldBorder().setSize(ConfigManager.MAP_SIZE_IN_CHUNKS * 16);

                FurnaceBattleRoyale.getNether().getWorldBorder().setCenter(0, 0);
                FurnaceBattleRoyale.getNether().getWorldBorder().setSize(ConfigManager.MAP_SIZE_IN_CHUNKS * 2);

                FurnaceBattleRoyale.getWorld().setSpawnLocation(0, 251, 0);

                FurnaceBattleRoyale.getWorld().setTime(10000);
                FurnaceBattleRoyale.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);

                createLobby(FurnaceBattleRoyale.getWorld(), 10, 250);

                break;

            case PLAYING_1:
                HandlerList.unregisterAll(GUIListener.getInstance());

                FurnaceBattleRoyale.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
                FurnaceBattleRoyale.getWorld().setSpawnLocation(LocationUtils.getDownBlock(new Location(FurnaceBattleRoyale.getWorld(), 0, 240, 0)));

                for(FBRPlayer fbrPlayer : PlayerManager.getPlayersList())
                {
                    fbrPlayer.setPlayerState(PlayerState.PLAYING);
                }

                destroyLobby(FurnaceBattleRoyale.getWorld(), 10, 250);

                CountdownTask.ExecutableCountdownAction update2 = (countdown) ->  {  };
                CountdownTask.ExecutableCountdownAction end2 = (countdown) ->
                {
                    GameManager.setGameState(GameState.PLAYING_2);
                };

                GameManager.createCountdownTask(60, update2, end2);

                break;
            case PLAYING_2:
                for(FBRTeam team : TeamManager.getAliveFBRTeams())
                {
                    team.setTeamChunk(team.getLeader().getPlayer().getLocation().getChunk());

                    if(!LocationUtils.isUniqueChunkForTeam(team) || !LocationUtils.isChunkInBorder(team.getTeamChunk())) {
                        Chunk closestChunk = LocationUtils.getClosestFreeChunk(team.getTeamChunk());

                        if (closestChunk == null) {
                            team.getLeader().getPlayer().sendMessage(ChatColor.RED + "[ВНИМАНИЕ] Все чанки вокруг заняты, выбираем рандомный чанк.....");
                            Chunk randomChunk = closestChunk.getWorld().getChunkAt(new Random().nextInt(ConfigManager.MAP_SIZE_IN_CHUNKS) - ConfigManager.MAP_SIZE_IN_CHUNKS / 2,
                                    new Random().nextInt(ConfigManager.MAP_SIZE_IN_CHUNKS) - ConfigManager.MAP_SIZE_IN_CHUNKS / 2);

                            closestChunk = LocationUtils.getClosestFreeChunk(randomChunk);
                        }
                        team.setTeamChunk(closestChunk);

                        team.getLeader().getPlayer().teleport(LocationUtils.getDownBlock(new Location(closestChunk.getWorld(), closestChunk.getX() * 16 + 8, 255, closestChunk.getZ() * 16 + 8)));

                    }

                    for(FBRPlayer fbrPlayer : team.getMembers())
                    {
                        if(fbrPlayer.getPlayer().getLocation().getChunk() != team.getTeamChunk() && !fbrPlayer.isLeader()) fbrPlayer.getPlayer().teleport(team.getLeader().getPlayer());

                        Chunk teamChunk = team.getTeamChunk();
                    }

                    Location furnaceLocation = new Location(team.getTeamChunk().getWorld(), team.getTeamChunk().getX() * 16, 255, team.getTeamChunk().getZ() * 16);
                    furnaceLocation.add(new Random().nextInt(16), 0, new Random().nextInt(16));
                    furnaceLocation = LocationUtils.getDownBlock(furnaceLocation);

                    team.createFurnace(furnaceLocation);
                }

                CountdownTask.ExecutableCountdownAction update3 = (countdown) ->  {  };
                CountdownTask.ExecutableCountdownAction end3 = (countdown) ->
                {
                    GameManager.setGameState(GameState.PLAYING_3);
                };

                GameManager.createCountdownTask(60, update3, end3);

                break;
            case PLAYING_3:

                break;
            case ENDED:
                FBRTeam wonTeam = TeamManager.getAliveFBRTeams().get(0);

                GameManager.stopCountdownTaskNoEnd();

                StringBuilder players = new StringBuilder();

                for(FBRPlayer fbrPlayer : wonTeam.getMembers())
                {
                    players.append(fbrPlayer.getName()).append(" ");
                    fbrPlayer.getPlayer().setInvulnerable(true);
                }
                String playersStr = players.toString();

                for(Player player : Bukkit.getOnlinePlayers())
                {
                    player.sendTitle("Команда " + wonTeam.getLeader().getName() + " побеждает", "Состав: " + playersStr, 20, 100, 20);
                }
                break;

        }

        for(FBRTeam team : TeamManager.getAliveFBRTeams())
        {
            team.updateScoreboardVisuals();
        }

        for(FBRPlayer fbrPlayer : PlayerManager.getPlayersList())
            fbrPlayer.updateWorldBorder();

        if(GameManager.isChunkPhase())
            GameManager.setPortalsAllowed(false);
        else if(ConfigManager.IS_PORTALS_ENABLED)
            GameManager.setPortalsAllowed(true);
    }

    public void createLobby(World world, int size, int height)
    {
        for(int x = -size; x <= size; x++) {
            for(int z = -size; z <= size; z++) {
                world.getBlockAt(x, height, z).setType(Material.GLASS);

                if(x == -size || x == size) {
                    for(int y = height + 1; y < height + 4; y++) {
                        world.getBlockAt(x, y, z).setType(Material.GLASS);
                    }
                }
                if(z == -size || z == size) {
                    for(int y = height + 1; y < height + 4; y++) {
                        world.getBlockAt(x, y, z).setType(Material.GLASS);
                    }
                }
            }
        }
    }

    public void destroyLobby(World world, int size, int height)
    {
        for(int x = -size; x <= size; x++) {
            for(int z = -size; z <= size; z++) {
                for(int y = height; y < height + 4; y++) {
                    if(world.getBlockAt(x,y,z).getType() == Material.AIR) continue;
                    world.getBlockAt(x, y, z).setType(Material.AIR);
                }
            }
        }
    }
}
