package com.lionarius.FBR.gui;

import com.lionarius.FBR.player.FBRPlayer;
import com.lionarius.FBR.team.FBRTeam;
import com.lionarius.FBR.team.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class TeamListGUI extends AbstractGUI {

    public int page;

    public TeamListGUI(FBRPlayer fbrPlayer, int page) {
        super(ChatColor.GREEN + "Список команд", 9*6);

        this.page = page;

        int maxPages = (int) Math.ceil((float)TeamManager.getFBRTeams().size() / 45);

        ExecutableGUIAction teamAction = (player, action, slot) ->
        {

        };

        for(int i = 45*page; i < Math.min(TeamManager.getFBRTeams().size(), 45*(page + 1)); i++)
        {
            setItem(getTeamItem(TeamManager.getFBRTeams().get(i)), i - (45 * page), teamAction);
        }

        ExecutableGUIAction backAction = (player, action, slot) ->
        {
            new TeamListGUI(player, page - 1);
        };

        ExecutableGUIAction nextAction = (player, action, slot) ->
        {
            new TeamListGUI(player, page + 1);
        };

        if(page > 0) setItem(getBackItem(), 45, backAction);
        if(page < maxPages - 1) setItem(getNextItem(), 53, nextAction);

        ExecutableGUIAction menuAction = (player, action, slot) ->
        {
            new MenuGUI(player);
        };
        setItem(getMenuItem(), 49, menuAction);

        fbrPlayer.openInventory(this);
    }

    private ItemStack getTeamItem(FBRTeam fbrTeam)
    {
        FBRPlayer leader = fbrTeam.getLeader();

        ItemStack teamItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta teamItemMeta = (SkullMeta) teamItem.getItemMeta();
        teamItemMeta.setOwningPlayer(leader.getPlayer());
        teamItemMeta.setDisplayName(leader.getName());
        teamItem.setItemMeta(teamItemMeta);
        return teamItem;
    }

    private ItemStack getBackItem()
    {
        ItemStack backItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta backItemMeta = backItem.getItemMeta();
        backItemMeta.setDisplayName("Назад");
        backItem.setItemMeta(backItemMeta);
        return backItem;
    }

    private ItemStack getNextItem()
    {
        ItemStack nextItem = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta nextItemMeta = nextItem.getItemMeta();
        nextItemMeta.setDisplayName("Вперед");
        nextItem.setItemMeta(nextItemMeta);
        return nextItem;
    }

    private ItemStack getMenuItem()
    {
        ItemStack menuItem = new ItemStack(Material.COMPASS);
        ItemMeta menuItemMeta = menuItem.getItemMeta();
        menuItemMeta.setDisplayName("В меню");
        menuItem.setItemMeta(menuItemMeta);
        return menuItem;
    }
}
