package jcn.jclan.plugins;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import jcn.jclan.JClans;
import jcn.jclan.utilities.DatabaseMethods;

import java.sql.Connection;

public class PlaceholderApiPlugin extends PlaceholderExpansion {
    private JClans plugin;
    private Connection connection;
    public PlaceholderApiPlugin(JClans plugin, Connection connection){
        this.plugin = plugin;
        this.connection = connection;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "clan";
    }

    @Override
    public @NotNull String getAuthor() {
        return "The_JCN";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("prefix")) {
            String playerName = player.getName();
            String clanPrefix = String.valueOf(new DatabaseMethods(connection).getClanPrefix(Bukkit.getPlayer(playerName)));
            if (!clanPrefix.isEmpty()) {
                String coloredPrefix = "&r[" + clanPrefix.toUpperCase() + "&r]";
                return coloredPrefix;
            } else {
                return "";
            }
        }
        return null;
    }
}
