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

public enum Executables implements CommandExecutables {
	
	GET(new Get()),
	REQUEST(new Request()),
	TELL(new Tell()),
	BROADCAST(new Broadcast()),
	ME(new Me());
	
	private Implementation implement;
	
	private Executables(Implementation implement) {
		this.implement = implement;
	}
	
	@Override
	public Implementation getImplementation() {
		return this.implement;
	}
	
	private static class Get extends Implementation {

		@Override
		public String getName() {
			return "getlocation";
		}

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
	
	private static class Request extends Implementation {

		@Override
		public String getName() {
			return "requestlocation";
		}

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
	
	private static class Tell extends Implementation {

		@Override
		public String getName() {
			return "telllocation";
		}

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
	
	private static class Broadcast extends Implementation {

		@Override
		public String getName() {
			return "broadcastlocation";
		}

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
	
	private static class Me extends Implementation {

		@Override
		public String getName() {
			return "mylocation";
		}

		@Override
		public boolean doPlayerCommand(CommandInstance instance) {
			instance.sendMessage("You are currently at %sloc");
			return true;
		}
		
	}

}
