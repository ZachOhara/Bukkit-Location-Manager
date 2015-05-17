/* PlayerCommands.java | Implementations of all the added commands
 * as they may be sent by a player inside of the game.
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

import com.zachohara.location.dataform.CommandInstance;

public class PlayerCommands {

	public static boolean get(CommandInstance instance) {
		return true;
	}

	public static boolean request(CommandInstance instance) {
		return true;
	}

	public static boolean tell(CommandInstance instance) {
		return true;
	}

	public static boolean broadcast(CommandInstance instance) {
		return true;
	}

	public static boolean me(CommandInstance instance) {
		return true;
	}

	public static boolean help(CommandInstance instance) {
		return true;
	}

}
