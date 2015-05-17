/* CommandInstance.java | Represents some details or conditions for
 * a single invocation of any command.
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

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandInstance {
	
	public CommandSender sender;
	public Player senderPl;
	public String senderName;
	public boolean fromConsole;
	
	public String command;
	
	public OfflinePlayer target;
	public Player targetPl;
	public String targetName;
	public boolean targetGiven;
	public boolean targetOnline;

	public CommandInstance(CommandSender sender, String command, OfflinePlayer target) {
		this(sender, command);
		this.target = target;
		if (target.getPlayer() != null) {
			this.targetPl = target.getPlayer();
			this.targetName = this.targetPl.getName();
			this.targetOnline = false;
		}
	}

	public CommandInstance(CommandSender sender, String command) {
		this.sender = sender;
		if (sender instanceof Player) {
			this.senderPl = (Player) sender;
			this.senderName = senderPl.getName();
			this.fromConsole = false;
		} else {
			this.senderName = "The Console";
			this.fromConsole = true;
		}
		this.command = command;
		this.targetName = "";
		this.targetOnline = false;	
	}
	
	// A shortcut to reporting error messages
	public void error(String message) {
		this.sender.sendMessage(ChatColor.RED + message);
	}
	
	public void targetError(String message) {
		this.sender.sendMessage(ChatColor.WHITE + this.targetName + ChatColor.RED + message);
	}

}
