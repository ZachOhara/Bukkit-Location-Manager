package io.github.zachohara.bukkit.location.locdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LocationDataManager {
	
	private File dataFile;
	private Map<String, SerializableLocation> locationMap;
	
	private static final String LOCATION_FILE_PATH = "offline_player_location_data.txt";
	
	public LocationDataManager(JavaPlugin owner) {
		this.dataFile = new File(owner.getDataFolder(), LOCATION_FILE_PATH);
		try {
			owner.getLogger().warning(this.dataFile.toString());
			this.dataFile.getParentFile().mkdirs();
			this.dataFile.createNewFile();
		} catch (IOException e1) {
			owner.getLogger().warning("Error creating new offline data file");
			e1.printStackTrace();
		}
		this.locationMap = new HashMap<String, SerializableLocation>();
		try {
			this.loadFromFile();
		} catch (ClassNotFoundException | IOException e) {
			owner.getLogger().warning("Offline player location data could not be read!");
			e.printStackTrace();
		}
	}
	
	public void saveAllPlayers() {
		for (Player p : Bukkit.getServer().getOnlinePlayers())
			this.savePlayerLocation(p);
	}
	
	public void savePlayerLocation(Player p) {
		String playername = p.getName().toLowerCase();
		if (this.playerLocationExists(playername))
			this.locationMap.remove(playername);
		this.locationMap.put(playername, new SerializableLocation(p.getLocation()));
	}
	
	public Location retrievePlayerLocation(String playername) {
		return this.locationMap.get(playername.toLowerCase()).getLocation();
	}
	
	public boolean playerLocationExists(String playername) {
		return this.locationMap.get(playername.toLowerCase()) != null;
	}
	
	public void saveToFile() throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.dataFile));
		out.writeObject(this.locationMap);
		out.close();
	}
	
	@SuppressWarnings("unchecked")
	public void loadFromFile() throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.dataFile));
		Object loadedMap = in.readObject();
		in.close();
		if (loadedMap instanceof Map<?, ?>)
			this.locationMap = (Map<String, SerializableLocation>) loadedMap;
		else
			throw new IOException("Object found was not of the correct type");
	}
	
	private static class SerializableLocation implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		String worldName;
		double x;
		double y;
		double z;
		float yaw;
		float pitch;
		
		public SerializableLocation(Location loc) {
			this.worldName = loc.getWorld().getName();
			this.x = loc.getX();
			this.y = loc.getY();
			this.z = loc.getZ();
			this.yaw = loc.getYaw();
			this.pitch = loc.getPitch();
		}
		
		public Location getLocation() {
			return new Location(Bukkit.getWorld(this.worldName), this.x, this.y, this.z, this.yaw, this.pitch);
		}
		
	}

}
