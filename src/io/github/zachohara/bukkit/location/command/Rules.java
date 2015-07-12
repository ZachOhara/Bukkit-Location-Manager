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

package io.github.zachohara.bukkit.location.command;

import io.github.zachohara.bukkit.common.command.CommandRules;
import io.github.zachohara.bukkit.common.command.CommandRulesEntry;

/**
 * The {@code Rules} interface represents the set of commands supported by this
 * plugin, and contains a {@code CommandRulesEntry} for each command, which defines
 * information about the expected context of the command.
 * 
 * @author Zach Ohara
 */
public enum Rules implements CommandRules {

	GET(new CommandRulesEntry("getlocation", 1, 1, Source.OP_ONLY, Target.ALLOW_OFFLINE)),
	REQUEST(new CommandRulesEntry("requestlocation", 1, 1, Source.ALL, Target.ALL_ONLINE)),
	TELL(new CommandRulesEntry("telllocation", 0, 1, Source.PLAYER_ONLY, Target.ALL_ONLINE)),
	BROADCAST (new CommandRulesEntry("broadcastlocation", 0, 1, Source.ALL, Target.ALLOW_OFFLINE)),
	ME(new CommandRulesEntry("mylocation", 0, 0, Source.PLAYER_ONLY, Target.NONE));

	/**
	 * The {@code CommandRulesEntry} associated with this command.
	 */
	private CommandRulesEntry rulesEntry;

	/**
	 * Constructs a new {@code Rules} object with the given {@code CommandRulesEntry}.
	 * 
	 * @param rules the {@code CommandRulesEntry} for this command.
	 */
	private Rules(CommandRulesEntry rules) {
		this.rulesEntry = rules;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CommandRulesEntry getRulesEntry() {
		return this.rulesEntry;
	}

}
