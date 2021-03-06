package com.lionarius.FBR.player;

import com.lionarius.FBR.FurnaceBattleRoyale;
import com.lionarius.FBR.events.PlayerStateChangedEvent;
import com.lionarius.FBR.game.GameManager;
import com.lionarius.FBR.gui.AbstractGUI;
import com.lionarius.FBR.player.invites.Invite;
import com.lionarius.FBR.tasks.PlayerOfflineTask;
import com.lionarius.FBR.team.FBRTeam;
import com.lionarius.FBR.utils.WorldBorderUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FBRPlayer {

    private Player player;
    private final String name;
    private final UUID uuid;
    private PlayerState state;
    private boolean isLeader;
    private FBRTeam team;
    private final List<Invite> invites = new ArrayList<>();
    private AbstractGUI currentGUI = null;
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

    public void setPlayerScoreboard(Scoreboard scoreboard) {
        this.getPlayer().setScoreboard(scoreboard);
    }

    public void playerLeftMidgame() {
        offlineTask = new PlayerOfflineTask(this);
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
                } else
                    WorldBorderUtils.resetWorldBorder(player);
            }
        }.runTaskLater(FurnaceBattleRoyale.getInstance(), 1L);
    }

    public void invitePlayer(Invite invite) {
        boolean contains = false;

        for (Invite invitation : invites) {
            if (invite.equals(invitation)) {
                contains = true;
                break;
            }
        }

        if (!contains)
            invites.add(invite);
    }

    public void acceptInvite(Invite invite) {
        if (invites.contains(invite)) {
            invite.getFromTeam().addPlayer(this);
        }
        invites.remove(invite);
    }

    public void declineInvite(Invite invite) {
        invites.remove(invite);
    }

    public List<Invite> getInvites() {
        return invites;
    }
}
