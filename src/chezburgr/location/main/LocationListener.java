package chezburgr.location.main;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class LocationListener extends JavaPlugin implements Listener{

	@EventHandler
	public void PlayerQuit(PlayerQuitEvent event) {
		storeLocationEvent(event);
	}
	
	@EventHandler
	public void PlayerKick(PlayerKickEvent event) {
		storeLocationEvent(event);
	}
	
	@EventHandler
	public void PlayerJoin(PlayerJoinEvent event) {
		storeLocationEvent(event);
	}
	
	@EventHandler
	public void PlayerMove(PlayerMoveEvent event) {
		storeLocationEvent(event);
	}
	
	public void storeLocationEvent(PlayerEvent event) {
		storeLocation(event.getPlayer());
	}
	
	public void storeLocation(Player player) {
		Location loc = player.getLocation();
		int[] coords = { loc.getBlockX(),
				loc.getBlockY(),
				loc.getBlockZ()
		};
		this.getConfig().set("playerlocation." + player.getName().toLowerCase(), coords);
	}

}
