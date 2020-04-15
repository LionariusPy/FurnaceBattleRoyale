package com.lionarius.FBR.gui;

import com.lionarius.FBR.player.FBRPlayer;
import com.lionarius.FBR.player.PlayerManager;
import com.lionarius.FBR.player.invites.Invite;
import com.lionarius.FBR.team.FBRTeam;
import com.lionarius.FBR.team.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerListGUI extends AbstractGUI{

    public int page;

    public PlayerListGUI(FBRPlayer fbrPlayer, int page) {
        super(ChatColor.AQUA + "Список команд", 9*6);
        this.page = page;

        int maxPages = (int) Math.ceil((float)PlayerManager.getPlayersList().size() / 45);

        ExecutableGUIAction playerAction = (player, action, slot) ->
        {
            PlayerListGUI currentGUI = (PlayerListGUI) player.getCurrentGUI();

            FBRPlayer clickedPlayer = PlayerManager.getPlayersList().get(slot + currentGUI.page * 45);
            new Invite(player.getTeam(), clickedPlayer);

            player.getPlayer().sendMessage(ChatColor.AQUA.toString() + "Приглашение игроку " + ChatColor.GOLD.toString() + clickedPlayer.getName() + ChatColor.AQUA.toString() + " отправлено");
        };

        for(int i = 45*page; i < Math.min(PlayerManager.getPlayersList().size(), 45*(page + 1)); i++)
        {
            setItem(getPlayerItem(PlayerManager.getPlayersList().get(i)), i - (45 * page), playerAction);
        }

        ExecutableGUIAction backAction = (player, action, slot) ->
        {
            new PlayerListGUI(player, page - 1);
        };

        ExecutableGUIAction nextAction = (player, action, slot) ->
        {
            new PlayerListGUI(player, page + 1);
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

    private ItemStack getPlayerItem(FBRPlayer fbrPlayer)
    {
        ItemStack teamItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta teamItemMeta = (SkullMeta) teamItem.getItemMeta();
        teamItemMeta.setOwningPlayer(fbrPlayer.getPlayer());
        teamItemMeta.setDisplayName(fbrPlayer.getName());
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
