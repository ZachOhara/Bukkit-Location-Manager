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

import io.github.zachohara.bukkit.common.command.CommandExecutables;
import io.github.zachohara.bukkit.common.command.CommandRules;
import io.github.zachohara.bukkit.common.plugin.CommonPlugin;
import io.github.zachohara.bukkit.location.data.LocationDataMap;

/**
 * The {@code Main} class is the entry point for plugin.
 *
 * @author Zach Ohara
 */
public class LocationManagerPlugin extends CommonPlugin {

	/**
	 * The current {@code LocationDataMap} for this plugin.
	 */
	private static LocationDataMap activeManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onEnable() {
		super.onEnable();
		LocationManagerPlugin.activeManager = new LocationDataMap(this);
		this.getServer().getPluginManager()
				.registerEvents(new LocationListener(LocationManagerPlugin.activeManager), this);
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

	/**
	 * Gets the currently active {@code LocationDataMap} for this plugin.
	 *
	 * @return the active {@code LocationDataMap}.
	 */
	public static LocationDataMap getLocationData() {
		return LocationManagerPlugin.activeManager;
	}

}
