package com.lionarius.FBR.player.invites;

import com.lionarius.FBR.player.FBRPlayer;
import com.lionarius.FBR.team.FBRTeam;

public class Invite {

    private final FBRTeam fromTeam;

    public Invite(FBRTeam fromTeam, FBRPlayer player) {
        this.fromTeam = fromTeam;
        player.invitePlayer(this);
    }

    public FBRTeam getFromTeam() {
        return fromTeam;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Invite compare = (Invite) obj;

        return this.fromTeam == compare.fromTeam;
    }
}
