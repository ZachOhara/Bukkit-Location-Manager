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

package io.github.zachohara.bukkit.location;

import io.github.zachohara.bukkit.location.data.LocationDataMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * A {@code LocationListener} is a {@code Listener} that will listen for events concerning
 * the connection status of players. Whenever the location status of a player changes
 * (because the player joined, left, was kicked, ect.), the event is forwarded to a
 * {@code LocationDataManager} object, which will save the location of the player.
 *
 * @author Zach Ohara
 */
public class LocationListener implements Listener {

	/**
	 * The {@code LocationDataManager} that all events should be reported to.
	 *
	 * @see LocationDataMap
	 */
	private LocationDataMap manager;

	/**
	 * Constructs a new Listener that will report actions to the given
	 * {@code LocationDataManager}.
	 *
	 * @param manager the {@code LocationDataManager} to report actions to.
	 */
	public LocationListener(LocationDataMap manager) {
		this.manager = manager;
	}

	/**
	 * Saves a player's location whenever the player joins the server.
	 *
	 * @param e the event that contains a player.
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent e) {
		this.manager.saveKeyedData(e.getPlayer());
	}

	/**
	 * Saves a player's location whenever the player leaves the server.
	 *
	 * @param e the event that contains a player.
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent e) {
		this.manager.saveKeyedData(e.getPlayer());
	}

	/**
	 * Saves a player's location whenever the player is kicked from the server.
	 *
	 * @param e the event that contains a player.
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerKick(PlayerKickEvent e) {
		this.manager.saveKeyedData(e.getPlayer());
	}

}
