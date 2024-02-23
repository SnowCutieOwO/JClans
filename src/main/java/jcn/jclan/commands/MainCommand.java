package jcn.jclan.commands;

import net.luckperms.api.LuckPerms;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import jcn.jclan.JClan;
import jcn.jclan.subCommands.*;

import java.sql.Connection;

import static jcn.jclan.utilities.PluginVocab.PLUGINPREFIX;

public class MainCommand implements CommandExecutor {
    private Connection connection;
    private LuckPerms luckPerms;
    private JClan plugin;
    private NamespacedKey key;
    public MainCommand(Connection connection, LuckPerms luckPerms, JClan plugin, NamespacedKey key){
        this.connection = connection;
        this.luckPerms = luckPerms;
        this.plugin = plugin;
        this.key = key;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if (strings.length == 0) {
            player.sendMessage(ChatColor.GOLD + PLUGINPREFIX + ChatColor.RED + " Неизвестная команд! Для просмотра всех команд используйте: /clan help!");
            return false;
        }
        switch (strings[0]) {
            case "invite":
                if (strings.length < 2) {
                    player.sendMessage(ChatColor.GOLD + PLUGINPREFIX + ChatColor.RED + " Использование: /clan invite (ник игрока)");
                    return true;
                }
                InviteClanCommand inviteAcceptCommand = new InviteClanCommand(connection, luckPerms, plugin);
                inviteAcceptCommand.sendInvite(player, strings);
                break;

            case "kick":
                if (strings.length < 2) {
                    player.sendMessage(ChatColor.GOLD + PLUGINPREFIX + ChatColor.RED + " Использование: /clan kick (ник игрока)");
                    return true;
                }
                KickCommand kickCommand = new KickCommand(connection, luckPerms);
                kickCommand.kickMember(player, strings);
                break;

            case "leave":
                LeaveCommand leaveCommand = new LeaveCommand(connection, luckPerms);
                leaveCommand.leave(player);
                break;

            case "help":
                HelpCommand helpCommand = new HelpCommand();
                helpCommand.HelpCommand(player);
                break;

            case "list":
                ClanListCommand clanListCommand = new ClanListCommand(connection);
                clanListCommand.ClanList(player);
                break;

            case "delete":
                DeleteClanCommand deleteCommand = new DeleteClanCommand(connection);
                deleteCommand.DeleteClan(player);
                break;

            case "create":
                CreateClanCommand clanMethod = new CreateClanCommand(connection, luckPerms);
                clanMethod.ClanCreate(player, strings);
                break;

            case "gui":
                GuiCommand guiCommand = new GuiCommand(connection, plugin, key);
                guiCommand.openGui(player);
                break;

            default:
                player.sendMessage(ChatColor.GOLD + PLUGINPREFIX + ChatColor.RED + " Неизвестная команд! Для просмотра всех команд используйте: /clan help!");
        }
        return false;
    }
}
