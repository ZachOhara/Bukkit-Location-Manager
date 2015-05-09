/* Main.java | The primary file for running the program
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

package com.zachohara.location.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.zachohara.location.data.Commands;
import com.zachohara.location.data.ConsoleCommands;
import com.zachohara.location.data.PlayerCommands;
import com.zachohara.location.dataform.CommandInstance;
import com.zachohara.location.dataform.CommandProperties;

public class Main extends JavaPlugin {

	public static Main plugin;
	
	public void onLoad() {
		// TODO: Get all player locations and store them
	}
	
	public int prepare(CommandInstance instance, CommandProperties properties) {
		if ((properties.useTarget && properties.targetRequired) && !instance.targetGiven) {
			instance.error("Not enough arguments");
			return 0;
		} else if (!properties.useTarget && instance.targetGiven) {
			instance.error("Too many arguments");
			return 0;
		}
		if (properties.opsOnly && !instance.fromConsole && instance.senderPl.isOp()) {
			instance.error("You must be an OP to use this command");
			return 1;
		}
		if (!properties.allowConsole && instance.fromConsole) {
			instance.error("You cannot use this command from the console");
			return 1;
		}
		if (!properties.allowOffineTarget && !instance.targetOnline) {
			instance.targetError(" either does not exist or is not online");
			return 1;
		}
		return 2;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender rawSender, Command rawCommand, String commandLabel, String[] args) {
		
		String commandStr = getOperation(rawSender, rawCommand, args);
		if (commandStr == null)
			return false;
		
		CommandInstance instance;
		
		//find the target player
		
		if (rawCommand.getName().equalsIgnoreCase("location")) {
			if (args.length == 2) {
				instance = new CommandInstance(rawSender, commandStr, Bukkit.getOfflinePlayer(args[1]));
			} else if (args.length == 1) {
				instance = new CommandInstance(rawSender, commandStr);
			} else if (args.length == 0) {
				rawSender.sendMessage(ChatColor.RED + "Not enough arguments");
				return false;
			} else {
				rawSender.sendMessage(ChatColor.RED + "Too many arguments");
				return false;
			}
		} else {
			if (args.length == 1) {
				instance = new CommandInstance(rawSender, commandStr, Bukkit.getOfflinePlayer(args[0]));
			} else if (args.length == 0) {
				instance = new CommandInstance(rawSender, commandStr);
			} else {
				rawSender.sendMessage(ChatColor.RED + "Too many arguments");
				return false;
			}
		}
		
		if (instance.command.equals("get")) {
			int result = prepare(instance, Commands.GET);
			if (result == 0) {
				return false;
			} else if (result == 1) {
				return true;
			} else if (result == 2) {
				if (instance.fromConsole) {
					return ConsoleCommands.get(instance);
				} else {
					return PlayerCommands.get(instance);
				}
			}
		}
		
		if (instance.command.equals("request")) {
			int result = prepare(instance, Commands.REQUEST);
			if (result == 0) {
				return false;
			} else if (result == 1) {
				return true;
			} else if (result == 2) {
				if (instance.fromConsole) {
					return ConsoleCommands.request(instance);
				} else {
					return PlayerCommands.request(instance);
				}
			}
		}
		
		if (instance.command.equals("tell")) {
			int result = prepare(instance, Commands.TELL);
			if (result == 0) {
				return false;
			} else if (result == 1) {
				return true;
			} else if (result == 2) {
				if (instance.fromConsole) {
					return ConsoleCommands.tell(instance);
				} else {
					return PlayerCommands.tell(instance);
				}
			}
		}
		
		if (instance.command.equals("broadcast")) {
			int result = prepare(instance, Commands.BROADCAST);
			if (result == 0) {
				return false;
			} else if (result == 1) {
				return true;
			} else if (result == 2) {
				if (instance.fromConsole) {
					return ConsoleCommands.broadcast(instance);
				} else {
					return PlayerCommands.broadcast(instance);
				}
			}
		}
		
		if (instance.command.equals("me")) {
			int result = prepare(instance, Commands.ME);
			if (result == 0) {
				return false;
			} else if (result == 1) {
				return true;
			} else if (result == 2) {
				if (instance.fromConsole) {
					return ConsoleCommands.me(instance);
				} else {
					return PlayerCommands.me(instance);
				}
			}
		}
		
		if (instance.command.equals("help")) {
			int result = prepare(instance, Commands.HELP);
			if (result == 0) {
				return false;
			} else if (result == 1) {
				return true;
			} else if (result == 2) {
				if (instance.fromConsole) {
					return ConsoleCommands.help(instance);
				} else {
					return PlayerCommands.help(instance);
				}
			}
		}
		
		return true;		
	}
	
	public String getOperation(CommandSender rawSender, Command rawCommand, String[] args) {
		
		String commandStr;
		
		switch(rawCommand.getName().toLowerCase()) {
		case "locget": 
			commandStr = "get";
			break;
		case "locreq": 
			commandStr = "request";
			break;
		case "loctell":
			commandStr = "tell";
			break;
		case "locbroad":
			commandStr = "broadcast";
			break;
		case "locme":
			commandStr = "me";
			break;
		case "lochelp":
			commandStr = "help";
			break;
		default:
			rawSender.sendMessage("A fatal error has occurred");
			commandStr = "error";
			break;
		case "location":
			switch(args[0].toLowerCase()) {
			case "g":
			case "get":
				commandStr = "get";
				break;
			case "r":
			case "request":
				commandStr = "request";
				break;
			case "t":
			case "tell":
				commandStr = "tell";
				break;
			case "b":
			case "broadcast":
				commandStr = "broadcast";
				break;
			case "m":
			case "me":
				commandStr = "me";
				break;
			case "h":
			case "?":
			case "help":
			case "":
				commandStr = "help";
				break;
			default:
				rawSender.sendMessage(ChatColor.RED + "\"" + args[0] + ChatColor.RED + "\" is not a valid operation");
				rawSender.sendMessage(ChatColor.RED + "Type \"/location help\" to see possible operations");
				commandStr = "error";
			}
		}
		return commandStr;
	}

}