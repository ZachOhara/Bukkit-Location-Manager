/* Commands.java | Contains the properties defined for every command.
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

package com.zachohara.location.data;

import com.zachohara.location.dataform.CommandProperties;

public interface Commands {

	public CommandProperties GET = new CommandProperties(true, true, true, true, true);
	public CommandProperties REQUEST = new CommandProperties(true, false, false, false, true);
	public CommandProperties ACCEPT = new CommandProperties(false, false, false, false, false);
	public CommandProperties DENY = new CommandProperties(false, false, false, false, false);
	public CommandProperties TELL = new CommandProperties(true, false, false, false, true);
	public CommandProperties BROADCAST = new CommandProperties(true, false, true, true, false);
	public CommandProperties ME = new CommandProperties(false, false, false, false, false);
	public CommandProperties HELP = new CommandProperties(false, false, true, false, false);
	
}
