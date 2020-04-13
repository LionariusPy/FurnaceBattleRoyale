package com.lionarius.FBR.player;

import com.lionarius.FBR.FurnaceBattleRoyale;
import com.lionarius.FBR.game.GameManager;
import com.lionarius.FBR.gui.AbstractGUI;
import com.lionarius.FBR.tasks.PlayerOfflineTask;
import com.lionarius.FBR.team.FBRTeam;
import com.lionarius.FBR.utils.WorldBorderUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import com.lionarius.FBR.events.PlayerStateChangedEvent;

import java.util.UUID;

public class FBRPlayer {

    private Player player;
    private String name;
    private UUID uuid;
    private PlayerState state;
    private FBRFurnace furnace;
    private FBRTeam team;
    private AbstractGUI currentGUI = null;
    private boolean isLeader;
    private PlayerOfflineTask offlineTask;

    public FBRPlayer(Player player) {
        this.player = player;
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        setPlayerState(PlayerState.WAITING);
        setTeam(new FBRTeam(this));
        offlineTask = null;
    }

    public boolean isOnline() {
        return player.isOnline();
    }

    public FBRTeam getTeam() {
        return team;
    }

    public void setTeam(FBRTeam team) {
        this.team = team;
    }

    public FBRFurnace getFurnace() {
        return furnace;
    }

    public void setFurnace(FBRFurnace furnace) {
        this.furnace = furnace;
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public PlayerState getState() {
        return state;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public void updatePlayer() {
        this.player = Bukkit.getPlayer(uuid);
    }

    public void setPlayerState(PlayerState playerState) {
        if (playerState == this.state) return;

        PlayerState oldPlayerState = this.state;
        this.state = playerState;

        Bukkit.getPluginManager().callEvent(new PlayerStateChangedEvent(this, playerState));
    }

    public void openInventory(AbstractGUI inventory) {
        currentGUI = inventory;
        getPlayer().openInventory(inventory.getInventory());
    }

    public void closeInventory() {
        currentGUI = null;
    }

    public AbstractGUI getCurrentGUI() {
        return currentGUI;
    }

    public void updatePlayerVisuals(Scoreboard scoreboard) {
        this.getPlayer().setScoreboard(scoreboard);
    }

    public void playerLeftMidgame() {
        offlineTask = new PlayerOfflineTask(FurnaceBattleRoyale.getInstance(), this);
    }

    public void playerJoinedBack() {
        offlineTask.cancel();
        offlineTask = null;
    }

    public void updateWorldBorder() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (GameManager.isChunkPhase() && state != PlayerState.DEAD) {
                    WorldBorderUtils.setWorldBorderSize(player, 16);
                    WorldBorderUtils.setWorldBorderLocation(player, team.getTeamChunkLocation());
                }
                else
                    WorldBorderUtils.resetWorldBorder(player);
            }
        }.runTaskLater(FurnaceBattleRoyale.getInstance(), 1L);
    }
}
