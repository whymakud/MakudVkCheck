package dev.whymakud.vkcheck;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
    Connection con;
    Statement stmt;
    ResultSet rs;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		Bukkit.getConsoleSender().sendMessage("§r[§bMakudVkCheck§r] by §cwhymakud.github.io §renabled!");
		Bukkit.getPluginManager().registerEvents(this, this);
		checkPlayer("TestNicknameForCheckDatabaseConnection");
	}

    public boolean checkPlayer(String name) {
        String url = "jdbc:mysql://localhost:" + getConfig().getString("mysql.port") + "/" + getConfig().getString("mysql.database");
        String user = getConfig().getString("mysql.user");
        String password = getConfig().getString("mysql.password");
        String query = "select " + getConfig().getString("mysql.players-field") + " from " + getConfig().getString("mysql.table");

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String count = rs.getString(1);
                if (count.equals(name)) {
                	return true;
				} else {
					return false;
				}
            }

        } catch (SQLException sqlEx) {
        	Bukkit.getConsoleSender().sendMessage("§r[§bMakudVkCheck§r] §cDatabase connection error!");
        	Bukkit.getConsoleSender().sendMessage("§r[§bMakudVkCheck§r] §cError for admins:");
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
		return false;
    }
    
	@EventHandler
	public void check(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().toLowerCase().startsWith("/vkcheck")) {
			if (checkPlayer(e.getPlayer().getName())) {
				e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.check.vk_ok")));
				e.setCancelled(true);
			} else {
				e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.check.vk_null")));
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void reward(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().toLowerCase().startsWith("/vkreward")) {
			if (checkPlayer(e.getPlayer().getName())) {
				e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.reward.reward_vk_ok")));
				getConfig().getStringList("give-reward.commands").forEach((cmd) -> {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ChatColor.translateAlternateColorCodes('&', cmd.replace("%player%", e.getPlayer().getName())));
				});
				e.setCancelled(true);
			} else {
				e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.reward.reward_vk_null")));
				e.setCancelled(true);
			}
		}
	}
	
}
