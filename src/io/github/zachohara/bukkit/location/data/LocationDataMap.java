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

}
