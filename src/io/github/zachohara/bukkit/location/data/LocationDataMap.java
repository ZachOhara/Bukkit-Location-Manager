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

package io.github.zachohara.bukkit.location.data;

import io.github.zachohara.bukkit.simpleplugin.persistence.map.PersistentPlayerData;
import io.github.zachohara.bukkit.simpleplugin.plugin.SimplePlugin;
import io.github.zachohara.bukkit.simpleplugin.serializable.SerializableLocation;

import org.bukkit.entity.Player;

/**
 * A {@code LocationDataMap} is a {@code PersistentPlayerData} map that stores the location
 * of every player when they join or leave the game.
 *
 * @author Zach Ohara
 */
public class LocationDataMap extends PersistentPlayerData<SerializableLocation> {

	/**
	 * The name of the file to store all location data to.
	 */
	public static final String FILENAME = "offline_player_locations.dat";

	/**
	 * Constructs a new {@code LocationDataMap} with the given plugin as an owner.
	 *
	 * @param owner the {@code CommonPlugin} that owns this data map.
	 */
	public LocationDataMap(SimplePlugin owner) {
		super(owner, LocationDataMap.FILENAME);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SerializableLocation calculateDataValue(Player key) {
		return new SerializableLocation(key.getLocation());
	}

//	/**
//	 * Gets the {@code Location} of the specified {@code Player}.
//	 *
//	 * @param p the {@code Player} to find the {@code Location} of.
//	 * @return the {@code Location} of the specified {@code Player}.
//	 */
//	public Location retrievePlayerLocation(Player p) {
//		return this.getKeyData(p).getLocation();
//	}
//
//	/**
//	 * Gets the {@code Location} of the {@code Player} with the specified name.
//	 *
//	 * @param playername the name of the player to find the {@code Location} of.
//	 * @return the {@code Location} of the specified {@code Player}.
//	 */
//	public Location retrievePlayerLocation(String playername) {
//		return this.getKeyData(playername).getLocation();
//	}
//
//	/**
//	 * A {@code SerializableLocation} object stores the same information as a
//	 * {@code Location} object, but can be serialized if necessary. A
//	 * {@code SerializableLocation} object can translate itself between a {@code Location}
//	 * object an a {@code SerializableLocation} object.
//	 */
//	public static class SerializableLocation implements Serializable {
//
//		/**
//		 * The name of the world that the location is in.
//		 */
//		String worldName;
//
//		/**
//		 * The x-coordinate of the location.
//		 */
//		double x;
//
//		/**
//		 * The y-coordinate of the location.
//		 */
//		double y;
//
//		/**
//		 * The z-coordinate of the location.
//		 */
//		double z;
//
//		/**
//		 * The yaw of the location.
//		 */
//		float yaw;
//
//		/**
//		 * The pitch of the location.
//		 */
//		float pitch;
//
//		private static final long serialVersionUID = 1L;
//
//		/**
//		 * Constructs a new {@code SerializableLocation} out of the given {@code Location}.
//		 *
//		 * @param loc the {@code Location} to construct this object from.
//		 */
//		public SerializableLocation(Location loc) {
//			this.worldName = loc.getWorld().getName();
//			this.x = loc.getX();
//			this.y = loc.getY();
//			this.z = loc.getZ();
//			this.yaw = loc.getYaw();
//			this.pitch = loc.getPitch();
//		}
//
//		/**
//		 * Converts this {@code SerializableLocation} into a new {@code Location}
//		 *
//		 * @return a {@code Location} object represented by this
//		 * {@code SerializableLocation} object.
//		 */
//		public Location getLocation() {
//			return new Location(Bukkit.getWorld(this.worldName), this.x, this.y, this.z, this.yaw, this.pitch);
//		}
//
//	}

}
