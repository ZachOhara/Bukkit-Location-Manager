/* LocationListener.java | Records a player's location when they either
 * join, quit, more, or are kicked.
 * Copyright (C) 2014 Zach Ohara
 *
 * This file is part of the Chezburgr's Location Manager plugin for Bukkit
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

package com.zachohara.location.main;

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

public class LocationListener extends JavaPlugin implements Listener {

	public LocationListener(JavaPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void PlayerQuit(PlayerQuitEvent event) {
		storeLocation(event);
	}

	@EventHandler
	public void PlayerKick(PlayerKickEvent event) {
		storeLocation(event);
	}

	@EventHandler
	public void PlayerJoin(PlayerJoinEvent event) {
		storeLocation(event);
	}

	@EventHandler
	public void PlayerMove(PlayerMoveEvent event) {
		storeLocation(event);
	}

	public void storeLocation(PlayerEvent event) {
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
