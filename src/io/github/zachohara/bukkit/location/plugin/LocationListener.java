package io.github.zachohara.bukkit.location.plugin;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class LocationListener implements Listener {
	
	private static LocationListener activeListener;
	private JavaPlugin owner;
	
	public LocationListener(JavaPlugin owner) {
		activeListener = this;
		this.owner = owner;
	}
	
	public static LocationListener getActiveListener() {
		return activeListener;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent e) {
		this.savePlayerLocation(e.getPlayer());
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerKick(PlayerKickEvent e) {
		this.savePlayerLocation(e.getPlayer());
	}
	
	private void savePlayerLocation(Player p) {
		this.owner.getLogger().info(ChatColor.GOLD + "Logging location data for " + p.getName());
		String path = getPathName(p.getName());
		this.owner.getConfig().set(path, true);
		this.owner.getConfig().set(path + ".x", p.getLocation().getBlockX());
		this.owner.getConfig().set(path + ".y", p.getLocation().getBlockY());
		this.owner.getConfig().set(path + ".z", p.getLocation().getBlockZ());
		this.owner.getConfig().set(path + ".world", p.getWorld().getName());
	}
	
	public boolean playerLocationExists(String playername) {
		Set<String> keySet = this.owner.getConfig().getKeys(true);
		return keySet.contains(getPathName(playername));
	}
	
	public Location retrieveLocation(String playername) {
		String path = getPathName(playername);
		int x = this.owner.getConfig().getInt(path + ".x");
		int y = this.owner.getConfig().getInt(path + ".y");
		int z = this.owner.getConfig().getInt(path + ".z");
		World world = Bukkit.getServer().getWorld((String) this.owner.getConfig().get(path + ".world"));
		return new Location(world, x, y, z);
	}
	
	private static String getPathName(String playername) {
		return "playerdata." + playername.toLowerCase();
	}

}
