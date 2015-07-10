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

package io.github.zachohara.bukkit.location.plugin;

import java.io.IOException;

import io.github.zachohara.bukkit.common.command.CommandExecutables;
import io.github.zachohara.bukkit.common.command.CommandRules;
import io.github.zachohara.bukkit.common.plugin.CommonPlugin;
import io.github.zachohara.bukkit.location.command.Executables;
import io.github.zachohara.bukkit.location.command.Rules;
import io.github.zachohara.bukkit.location.locdata.LocationDataManager;
import io.github.zachohara.bukkit.location.locdata.LocationListener;

import org.bukkit.event.Listener;

public class Main extends CommonPlugin implements Listener {
	
	private static LocationDataManager activeManager;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onEnable() {
		activeManager = new LocationDataManager(this);
		activeManager.saveAllPlayers();
		this.getServer().getPluginManager().registerEvents(new LocationListener(activeManager), this);
	}

	@Override
	public void onDisable() {
		activeManager.saveAllPlayers();
		try {
			activeManager.saveToFile();
		} catch (IOException e) {
			this.getLogger().warning("Unable to save offline player location data:");
			e.printStackTrace();
		}
	}	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<? extends CommandRules> getCommandRuleSet() {
		return Rules.class;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<? extends CommandExecutables> getCommandExecutableSet() {
		return Executables.class;
	}
	
	public static LocationDataManager getLocationData() {
		return activeManager;
	}

}
