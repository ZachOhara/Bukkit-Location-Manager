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

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.zachohara.bukkit.common.command.CommandExecutables;
import io.github.zachohara.bukkit.common.command.CommandInstance;
import io.github.zachohara.bukkit.common.command.Implementation;
import io.github.zachohara.bukkit.common.util.StringUtil;
import io.github.zachohara.bukkit.location.locdata.LocationDataManager;
import io.github.zachohara.bukkit.location.locdata.LocationRequestHistory;
import io.github.zachohara.bukkit.location.plugin.Main;

/**
 * The {@code Executables} interface represents the set of commands supported by this
 * plugin, and contains an executable object for each command that acts as the main
 * procedure for the command.
 * 
 * @author Zach Ohara
 */
public enum Executables implements CommandExecutables {

	GET(new Get()),
	REQUEST(new Request()),
	TELL(new Tell()),
	BROADCAST(new Broadcast()),
	ME(new Me());

	/**
	 * The subclass of {@code Implementation} that contains an implementation for the
	 * command
	 */
	private Implementation implement;

	/**
	 * Constructs a new constant with the given implementation.
	 * 
	 * @param implement the implementation of the command.
	 */
	private Executables(Implementation implement) {
		this.implement = implement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Implementation getImplementation() {
		return this.implement;
	}

	/**
	 * The implementation for the 'getlocation' command.
	 */
	private static class Get extends Implementation {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getName() {
			return "getlocation";
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			LocationDataManager activeManager = Main.getLocationData();
			if (instance.hasTarget()) {
				instance.sendMessage("%t is at position %tloc");
			} else if (activeManager.playerLocationExists(instance.getGivenTarget())) {
				Location targetLoc = activeManager.retrievePlayerLocation(instance.getGivenTarget());
				String locString = StringUtil.getLocationString(targetLoc);
				instance.sendMessage("%gt is not currently online!\nTheir last known location is " + locString);
			} else {
				instance.sendError(StringUtil.ERROR_TARGET_DNE_MESSAGE);
			}
			return true;
		}

	}

	/**
	 * The implementation for the 'requestlocation' command.
	 */
	private static class Request extends Implementation {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getName() {
			return "requestlocation";
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			instance.sendMessage("%t has been informed of your request\n"
					+ "%t may now send you their location");
			instance.sendTargetMessage("%s has requested your location\n"
					+ "Use @name/telllocation@text or @name/tellloc@text to tell %s your location");
			LocationRequestHistory.registerRequest(instance);
			return true;
		}

	}

	/**
	 * The implementation for the 'telllocation' command.
	 */
	private static class Tell extends Implementation {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getName() {
			return "telllocation";
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			if (instance.hasTarget()) {
				instance.sendTargetMessage("%s is currently at %sloc");
				instance.sendMessage("%t has been informed of your location");
			} else {
				Player returnTo = LocationRequestHistory.getMostRecentRequest(instance.getSenderPlayer());
				if (returnTo != null) {
					instance.sendMessage("@name " + returnTo.getName() + "@text has been informed of your location") ;
					returnTo.sendMessage(StringUtil.parseString("%s is currently at %sloc", instance));
				} else {
					instance.sendError("No open requests were found");
				}
			}
			return true;
		}

	}

	/**
	 * The implementation for the 'broadcastlocation' command.
	 */
	private static class Broadcast extends Implementation {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getName() {
			return "broadcastlocation";
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			LocationDataManager activeManager = Main.getLocationData();
			if (instance.getArguments().length == 0)
				instance.broadcastMessage("%s is currently at position %sloc");
			else if (instance.hasTarget()) {
				instance.broadcastMessage("%t is currently at %tloc");
			} else if (activeManager.playerLocationExists(instance.getGivenTarget())) {
				Location targetLoc = activeManager.retrievePlayerLocation(instance.getGivenTarget());
				String locString = StringUtil.getLocationString(targetLoc);
				instance.broadcastMessage("%gt is not currently online!\nTheir last known location is " + locString);
			}
			else
				instance.sendError(StringUtil.ERROR_TARGET_DNE_MESSAGE);
			return true;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean doConsoleCommand(CommandInstance instance) {
			if (instance.getArguments().length == 0) {
				instance.sendError("The console has no location to broadcast");
				return true;
			} else {
				return this.doPlayerCommand(instance);
			}
		}

	}

	/**
	 * The implementation for the 'mylocation' command.
	 */
	private static class Me extends Implementation {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getName() {
			return "mylocation";
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			instance.sendMessage("You are currently at %sloc");
			return true;
		}

	}

}
