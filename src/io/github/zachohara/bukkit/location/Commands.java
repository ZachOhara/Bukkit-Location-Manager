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

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.zachohara.bukkit.location.data.LocationDataMap;
import io.github.zachohara.bukkit.location.data.LocationRequestHistory;
import io.github.zachohara.bukkit.simpleplugin.command.CommandInstance;
import io.github.zachohara.bukkit.simpleplugin.command.CommandSet;
import io.github.zachohara.bukkit.simpleplugin.command.Implementation;
import io.github.zachohara.bukkit.simpleplugin.command.Properties;
import io.github.zachohara.bukkit.simpleplugin.command.Properties.Source;
import io.github.zachohara.bukkit.simpleplugin.command.Properties.Target;
import io.github.zachohara.bukkit.simpleplugin.util.StringUtil;

/**
 * The {@code Commands} interface represents the set of commands supported by this plugin, and
 * contains a {@code Properties} object for each command.
 *
 * @author Zach Ohara
 * @see Properties
 */
public enum Commands implements CommandSet {

	GETLOCATION(new Properties(1, 1, Source.OP_ONLY, Target.ALLOW_OFFLINE, new Get())),
	REQUESTLOCATION(new Properties(1, 1, Source.ALL, Target.ALL_ONLINE, new Request())),
	TELLLOCATION(new Properties(0, 1, Source.PLAYER_ONLY, Target.ALL_ONLINE, new Tell())),
	BROADCASTLOCATION(new Properties(0, 1, Source.ALL, Target.ALLOW_OFFLINE, new Broadcast())),
	MYLOCATION(new Properties(0, 0, Source.PLAYER_ONLY, Target.NONE, new Me()));
	
	/**
	 * The {@code Properties} object specific to a single command.
	 */
	private Properties properties;
	
	/**
	 * Constructs a new {@code Commands} with the given {@code Properties} for this command. 
	 *
	 * @param p the {@code Properties} for this command.
	 */
	private Commands(Properties p) {
		this.properties = p;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Properties getProperties() {
		return this.properties;
	}

	/**
	 * The implementation for the 'getlocation' command.
	 */
	private static class Get extends Implementation {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			LocationDataMap activeManager = LocationManagerPlugin.getLocationData();
			if (instance.hasTarget()) {
				instance.sendMessage("%t is at position %tloc");
			} else if (activeManager.keyDataExists(instance.getGivenTarget().toLowerCase())) {
				Location targetLoc = activeManager.getKeyData(instance.getGivenTarget());
				String locString = StringUtil.getLocationString(targetLoc);
				instance.sendMessage("%gt is not currently online!\nTheir last known location is "
						+ locString);
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
		public boolean doPlayerCommand(CommandInstance instance) {
			if (instance.hasTarget()) {
				instance.sendTargetMessage("%s is currently at %sloc");
				instance.sendMessage("%t has been informed of your location");
			} else {
				Player returnTo = LocationRequestHistory.getMostRecentRequest(instance.getSenderPlayer());
				if (returnTo != null) {
					instance.sendMessage("@name " + returnTo.getName()
							+ "@text has been informed of your location");
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
		public boolean doPlayerCommand(CommandInstance instance) {
			LocationDataMap activeManager = LocationManagerPlugin.getLocationData();
			if (instance.getArguments().length == 0) {
				instance.broadcastMessage("%s is currently at position %sloc");
			} else if (instance.hasTarget()) {
				instance.broadcastMessage("%t is currently at %tloc");
			} else if (activeManager.keyDataExists(instance.getGivenTarget().toLowerCase())) {
				Location targetLoc = activeManager.getKeyData(instance.getGivenTarget());
				String locString = StringUtil.getLocationString(targetLoc);
				instance.broadcastMessage("%gt is not currently online!\nTheir last known location is "
						+ locString);
			} else {
				instance.sendError(StringUtil.ERROR_TARGET_DNE_MESSAGE);
			}
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
		public boolean doPlayerCommand(CommandInstance instance) {
			instance.sendMessage("You are currently at %sloc");
			return true;
		}

	}

}
