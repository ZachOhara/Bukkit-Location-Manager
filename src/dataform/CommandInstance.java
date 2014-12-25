package dataform;

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

}
