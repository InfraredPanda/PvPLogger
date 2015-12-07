package io.github.infraredpanda.pvplogger.listener;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import io.github.infraredpanda.pvplogger.PvPLogger;

public class ClientDisconnectListener
{
	@Listener
	public void onPlayerDisconnect(ClientConnectionEvent.Disconnect event)
	{
		Player disconnectingPlayer = event.getTargetEntity();

		if (PvPLogger.playersTakenDamage.contains(disconnectingPlayer.getUniqueId()))
		{
			PvPLogger.playersToPunish.add(disconnectingPlayer.getUniqueId());

			for (Player player : PvPLogger.game.getServer().getOnlinePlayers())
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "[PvPLogger]: ", TextColors.RED, disconnectingPlayer.getName() + " has disconnected during combat and will be punished when they return!"));
			}
		}
	}
}
