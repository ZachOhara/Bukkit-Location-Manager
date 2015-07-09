package io.github.zachohara.bukkit.location.plugin;

import io.github.zachohara.bukkit.common.command.CommandInstance;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Player;

public class LocationRequestHistory {

	private static List<Player[]> requestHistory = new LinkedList<Player[]>();

	public static void registerRequest(CommandInstance instance) {
		registerRequest(instance.getSenderPlayer(), instance.getTargetPlayer());
	}

	public static void registerRequest(Player sender, Player target) {
		for (int i = requestHistory.size() - 1; i >= 0; i--) {
			if (requestHistory.get(i)[1].equals(target))
				requestHistory.remove(i);
		}
		Player[] request = {sender, target};
		requestHistory.add(request);
	}

	public static Player getMostRecentRequest(Player target) {
		for (int i = requestHistory.size() - 1; i >= 0; i--) {
			Player[] pair = requestHistory.get(i);
			if (pair[1].equals(target))
				return pair[0];
		}
		return null;
	}

}
