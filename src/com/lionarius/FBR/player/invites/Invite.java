package com.lionarius.FBR.player.invites;

import com.lionarius.FBR.player.FBRPlayer;
import com.lionarius.FBR.team.FBRTeam;

public class Invite {

    private FBRTeam fromTeam;

    public Invite(FBRTeam fromTeam, FBRPlayer player)
    {
        this.fromTeam = fromTeam;
        player.invitePlayer(this);
    }

    public FBRTeam getFromTeam() {
        return fromTeam;
    }
}
