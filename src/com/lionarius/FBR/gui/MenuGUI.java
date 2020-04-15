package com.lionarius.FBR.gui;

import com.lionarius.FBR.config.ConfigManager;
import com.lionarius.FBR.player.FBRPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuGUI extends AbstractGUI {

    public MenuGUI(FBRPlayer fbrPlayer) {
        super(ChatColor.GREEN + "Меню", 9);

        if(fbrPlayer.isLeader()) {
            ItemStack ready = getReadyItem(fbrPlayer);
            ExecutableGUIAction readyAction = (player, action, slot) ->
            {
                player.getTeam().setReadyToStart(!player.getTeam().isReadyToStart());

                ItemStack readyItem = getReadyItem(player);

                getInventory().setItem(7, readyItem);
            };
            setItem(ready, 7, readyAction);
        }

        if(ConfigManager.MAX_PLAYERS_TEAM > 1) {
            ItemStack teamList = new ItemStack(Material.FURNACE);
            ItemMeta teamListItemMeta = teamList.getItemMeta();
            teamListItemMeta.setDisplayName(ChatColor.AQUA + "Список команд");
            teamList.setItemMeta(teamListItemMeta);
            ExecutableGUIAction teamListAction = (player, action, slot) ->
            {
                new TeamListGUI(player, 0);
            };
            setItem(teamList, 1, teamListAction);

            ItemStack playerList = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta playerListItemMeta = playerList.getItemMeta();
            playerListItemMeta.setDisplayName(ChatColor.AQUA + "Список игрков");
            playerList.setItemMeta(playerListItemMeta);
            ExecutableGUIAction playerListAction = (player, action, slot) ->
            {
                new PlayerListGUI(player, 0);
            };
            setItem(playerList, 2, playerListAction);

        }

        fbrPlayer.openInventory(this);
    }

    private ItemStack getReadyItem(FBRPlayer fbrPlayer)
    {
        ItemStack ready = new ItemStack(fbrPlayer.getTeam().isReadyToStart() ? Material.RED_CONCRETE : Material.GREEN_CONCRETE);
        ItemMeta readyItemMeta = ready.getItemMeta();
        readyItemMeta.setDisplayName(fbrPlayer.getTeam().isReadyToStart() ? ChatColor.RED + "Не готов" : ChatColor.GREEN + "Готов" );
        ready.setItemMeta(readyItemMeta);
        return ready;
    }

}
