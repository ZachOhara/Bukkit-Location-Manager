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

import io.github.zachohara.bukkit.common.command.CommandExecutables;
import io.github.zachohara.bukkit.common.command.CommandRules;
import io.github.zachohara.bukkit.common.plugin.CommonPlugin;
import io.github.zachohara.bukkit.location.command.Executables;
import io.github.zachohara.bukkit.location.command.Rules;
import io.github.zachohara.bukkit.location.locdata.LocationDataMap;
import io.github.zachohara.bukkit.location.locdata.LocationListener;

/**
 * The {@code Main} class is the entry point for plugin.
 * 
 * @author Zach Ohara
 */
public class Main extends CommonPlugin {

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
		activeManager = new LocationDataMap(this);
		this.getServer().getPluginManager().registerEvents(new LocationListener(activeManager), this);
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

	public static LocationDataMap getLocationData() {
		return activeManager;
	}

}
