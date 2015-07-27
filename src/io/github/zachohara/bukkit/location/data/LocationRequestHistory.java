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

import io.github.zachohara.bukkit.common.command.CommandInstance;
import io.github.zachohara.bukkit.common.util.PlayerUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * The {@code LocationRequestHistory} class contains static fields and methods that keep
 * track of useages of the 'requestlocation' command. By design, whenever a player uses the
 * command 'telllocation' without any arguments, their location is sent to whichever other
 * player on the server sent the most recent request for the sending player's location.
 *
 * @author Zach Ohara.
 */
public abstract class LocationRequestHistory {

	/**
	 * The list of location requests, in chronological order, and stored by the UUID's of
	 * the involved players.
	 */
	private static List<UUID[]> requestHistory = new LinkedList<UUID[]>();

	/**
	 * Registers a new instance of the 'requestlocation' command, and adds the UUID's of
	 * the relevant players to the list.
	 *
	 * @param instance the {@code CommandInstance} of the command.
	 */
	public static void registerRequest(CommandInstance instance) {
		LocationRequestHistory.registerRequest(instance.getSenderPlayer(), instance.getTargetPlayer());
	}

	/**
	 * Registers a new instance of the 'requestlocation' command, and adds the UUID's of
	 * the the two given players to the list.
	 *
	 * @param sender the player who sent the command.
	 * @param target the player that was targeted by the command.
	 */
	public static void registerRequest(Player sender, Player target) {
		for (int i = LocationRequestHistory.requestHistory.size() - 1; i >= 0; i--) {
			if (Bukkit.getPlayer(LocationRequestHistory.requestHistory.get(i)[1]).equals(target)) {
				LocationRequestHistory.requestHistory.remove(i);
			}
		}
		UUID[] request = {sender.getUniqueId(), target.getUniqueId()};
		LocationRequestHistory.requestHistory.add(request);
		PlayerUtil.getAdmin().sendMessage(
				"Registering request from " + sender.getName() + " to " + target.getName());
		PlayerUtil.getAdmin().sendMessage("Length is now " + LocationRequestHistory.requestHistory.size());
	}

	/**
	 * Finds the player who most recently send a location request to the given target.
	 *
	 * @param target the player to query as a recent target.
	 * @return the player who most recently requested the location of the given target.
	 */
	public static Player getMostRecentRequest(Player target) {
		for (int i = LocationRequestHistory.requestHistory.size() - 1; i >= 0; i--) {
			UUID[] pair = LocationRequestHistory.requestHistory.get(i);
			if (pair[1].equals(target.getUniqueId())) {
				return Bukkit.getPlayer(pair[0]);
			}
		}
		return null;
	}

}
