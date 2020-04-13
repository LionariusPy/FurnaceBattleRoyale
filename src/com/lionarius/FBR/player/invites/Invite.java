package com.lionarius.FBR.player.invites;

import com.lionarius.FBR.player.FBRPlayer;

public class Invite {

    private FBRPlayer player;

    public Invite(FBRPlayer player)
    {
        this.player = player;
    }

    public FBRPlayer getPlayer() {
        return player;
    }
}
