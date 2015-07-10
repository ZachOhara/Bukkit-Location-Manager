package io.github.zachohara.bukkit.location.locdata;

import io.github.zachohara.bukkit.common.command.CommandInstance;
import io.github.zachohara.bukkit.common.util.PlayerUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LocationRequestHistory {

	private static List<UUID[]> requestHistory = new LinkedList<UUID[]>();

	public static void registerRequest(CommandInstance instance) {
		registerRequest(instance.getSenderPlayer(), instance.getTargetPlayer());
	}

	public static void registerRequest(Player sender, Player target) {
		for (int i = requestHistory.size() - 1; i >= 0; i--) {
			if (Bukkit.getPlayer(requestHistory.get(i)[1]).equals(target))
				requestHistory.remove(i);
		}
		UUID[] request = {sender.getUniqueId(), target.getUniqueId()};
		requestHistory.add(request);
		PlayerUtil.getAdmin().sendMessage("Registering request from " + sender.getName() + " to " + target.getName());
		PlayerUtil.getAdmin().sendMessage("Length is now " + requestHistory.size());
	}

	public static Player getMostRecentRequest(Player target) {
		for (int i = requestHistory.size() - 1; i >= 0; i--) {
			UUID[] pair = requestHistory.get(i);
			if (pair[1].equals(target.getUniqueId()))
				return Bukkit.getPlayer(pair[0]);
		}
		return null;
	}

}
