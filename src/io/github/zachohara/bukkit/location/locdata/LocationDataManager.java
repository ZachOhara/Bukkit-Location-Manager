/* Copyright (C) 2015 Zach Ohara
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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

/**
 * A {@code LocationDataManager} object is responsible for managing and storing the
 * location information for all players that are currently offline.
 * 
 * @author Zach Ohara
 */
public class LocationDataManager {

	/**
	 * The address of the file that should be written to.
	 */
	private File dataFile;

	/**
	 * A {@code HashMap} of location data that is attached to a player's username.
	 */
	private Map<String, SerializableLocation> locationMap;

	/**
	 * The name of the file that location information should be written to.
	 */
	private static final String LOCATION_FILE_PATH = "offline_player_location_data.txt";

	/**
	 * Constructs a new {@code LocationDataManager} with the given plugin as an owner.
	 * 
	 * @param owner the plugin that created this object.
	 */
	public LocationDataManager(JavaPlugin owner) {
		this.dataFile = new File(owner.getDataFolder(), LOCATION_FILE_PATH);
		this.locationMap = new HashMap<String, SerializableLocation>();
		this.createDataFile(owner);
		this.attemptLoadFile(owner);
	}

	/**
	 * Saves the location data of all the players currently on the server.
	 */
	public void saveAllPlayers() {
		for (Player p : Bukkit.getServer().getOnlinePlayers())
			this.savePlayerLocation(p);
	}

	/**
	 * Saves the location data for a single player.
	 * 
	 * @param p the {@code Player} to save the location information for.
	 */
	public void savePlayerLocation(Player p) {
		String playername = p.getName().toLowerCase();
		if (this.playerLocationExists(playername))
			this.locationMap.remove(playername);
		this.locationMap.put(playername, new SerializableLocation(p.getLocation()));
	}

	/**
	 * Retrieves the stored location data for a player with the given name, and
	 * reconstructs that data into a {@code Location} object.
	 * 
	 * @param playername the name of the player to return the location of.
	 * @return the {@code Location} of the given player.
	 */
	public Location retrievePlayerLocation(String playername) {
		return this.locationMap.get(playername.toLowerCase()).getLocation();
	}

	/**
	 * Tests if there exists location data for a player with the given name.
	 * 
	 * @param playername the name of the player to test for.
	 * @return {@code true} if there is a record of the player's offline location;
	 * {@code false} otherwise.
	 */
	public boolean playerLocationExists(String playername) {
		return this.locationMap.get(playername.toLowerCase()) != null;
	}

	/**
	 * Saves all the stored location data into an external file, so that it can be
	 * reconstructed later even if the object information is dumped.
	 * 
	 * @throws IOException if the write operation fails.
	 */
	public void saveToFile() throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.dataFile));
		out.writeObject(this.locationMap);
		out.close();
	}

	/**
	 * Loads stored location data from an external file, so that it can be reconstructed
	 * into a usable object form.
	 * 
	 * @throws IOException if the file read operation fails.
	 * @throws ClassNotFoundException if the read is succesful, but the content is not
	 * what was expected.
	 */
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

	/**
	 * Creates a new file for location data if and only if there is not already stored
	 * location data under the same filename.
	 * 
	 * @param owner the {@code JavaPlugin} that should be informed of errors in this
	 * operation.
	 */
	private void createDataFile(JavaPlugin owner) {
		try {
			this.dataFile.getParentFile().mkdirs();
			this.dataFile.createNewFile();
		} catch (IOException e1) {
			owner.getLogger().warning("Error creating new offline data file!");
			owner.getLogger().warning("Location information will not be stored for offline players!");
		}
	}

	/**
	 * Attempts to load currently existant location data from a file.
	 * 
	 * @param owner the {@code JavaPlugin} that should be informed of errors in this
	 * operation.
	 */
	private void attemptLoadFile(JavaPlugin owner) {
		try {
			this.loadFromFile();
		} catch (ClassNotFoundException | IOException e) {
			owner.getLogger().warning("Offline player location data could not be read!");
			owner.getLogger().warning("Is this the first time the plugin is being used?");
		}
	}

	/**
	 * A {@code SerializableLocation} object stores the same information as a
	 * {@code Location} object, but can be serialized if necessary. A
	 * {@code SerializableLocation} object can translate itself between a
	 * {@code Location} object an a {@code SerializableLocation} object.
	 */
	private static class SerializableLocation implements Serializable {

		/**
		 * The name of the world that the location is in.
		 */
		String worldName;

		/**
		 * The x-coordinate of the location.
		 */
		double x;

		/**
		 * The y-coordinate of the location.
		 */
		double y;

		/**
		 * The z-coordinate of the location.
		 */
		double z;

		/**
		 * The yaw of the location.
		 */
		float yaw;

		/**
		 * The pitch of the location.
		 */
		float pitch;

		private static final long serialVersionUID = 1L;

		/**
		 * Constructs a new {@code SerializableLocation} out of the given {@code Location}.
		 * 
		 * @param loc the {@code Location} to construct this object from.
		 */
		public SerializableLocation(Location loc) {
			this.worldName = loc.getWorld().getName();
			this.x = loc.getX();
			this.y = loc.getY();
			this.z = loc.getZ();
			this.yaw = loc.getYaw();
			this.pitch = loc.getPitch();
		}

		/**
		 * Converts this {@code SerializableLocation} into a new {@code Location}
		 * 
		 * @return a {@code Location} object represented by this
		 * {@code SerializableLocation} object.
		 */
		public Location getLocation() {
			return new Location(Bukkit.getWorld(this.worldName), this.x, this.y, this.z, this.yaw, this.pitch);
		}

	}

}
