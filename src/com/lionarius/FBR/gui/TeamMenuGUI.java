package com.lionarius.FBR.gui;

import com.lionarius.FBR.player.FBRPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamMenuGUI extends AbstractGUI {

    public TeamMenuGUI(FBRPlayer fbrPlayer) {
        super(ChatColor.GREEN + "Командное меню", 9);

        ItemStack ready = getReadyItem(fbrPlayer);
        ExecutableGUIAction readyAction = (player, action) ->
        {
            player.getTeam().setReadyToStart(!player.getTeam().isReadyToStart());

            ItemStack readyItem = getReadyItem(player);

            getInventory().setItem(7, readyItem);
        };
        setItem(ready, 7, readyAction);

        ItemStack teamList = new ItemStack(Material.FURNACE);
        ItemMeta teamListItemMeta = teamList.getItemMeta();
        teamListItemMeta.setDisplayName(ChatColor.AQUA + "Список команд");
        teamList.setItemMeta(teamListItemMeta);
        ExecutableGUIAction teamListAction =  (player, action) ->
        {
            new TeamListGUI(player, 0);
        };
        setItem(teamList, 1, teamListAction);

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
