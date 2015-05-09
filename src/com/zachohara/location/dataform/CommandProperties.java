/* CommandProperties.java | A collection of properties, such as
 * permissions, that may be unique for any command.
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

package com.zachohara.location.dataform;

public class CommandProperties {

	public boolean useTarget;
	public boolean opsOnly;
	public boolean allowConsole;
	public boolean allowOffineTarget;
	public boolean targetRequired;
	
	public CommandProperties(boolean useTarget, boolean opsOnly, boolean allowConsole, boolean allowOfflineTarget, boolean targetRequired) {
		this.useTarget = useTarget;
		this.opsOnly = opsOnly;
		this.allowConsole = allowConsole;
		this.allowOffineTarget = allowOfflineTarget;
		this.targetRequired = targetRequired;
	}
	
}
