package io.github.infraredpanda.pvplogger.listener;

import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import io.github.infraredpanda.pvplogger.PvPLogger;
import io.github.infraredpanda.pvplogger.utils.ConfigManager;

public class ClientJoinListener
{
	@Listener
	public void onPlayerJoin(ClientConnectionEvent.Join event)
	{
		Player joinedPlayer = event.getTargetEntity();

		if (PvPLogger.playersToPunish.contains(joinedPlayer.getUniqueId()))
		{
			if(PvPLogger.playersTakenDamage.contains(joinedPlayer.getUniqueId()))
			{
				PvPLogger.playersTakenDamage.remove(joinedPlayer.getUniqueId());
			}
			PvPLogger.playersToPunish.remove(joinedPlayer.getUniqueId());

			Optional<Object> optionalPunishmentCommand = ConfigManager.getConfigValue("pvplogger.punishment");

			if (optionalPunishmentCommand.isPresent())
			{
				String punishCommand = (String) optionalPunishmentCommand.get();
				punishCommand = punishCommand.replaceAll("@p", joinedPlayer.getName());
				PvPLogger.game.getCommandManager().process(PvPLogger.game.getServer().getConsole(), punishCommand);
				joinedPlayer.sendMessage(Texts.of(TextColors.DARK_RED, "[PvPLogger]: ", TextColors.RED, "You have been punished for disconnecting during combat!"));
			}

			for (Player player : PvPLogger.game.getServer().getOnlinePlayers())
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "[PvPLogger]: ", TextColors.RED, joinedPlayer.getName() + " disconnected during combat and has been punished!"));
			}
		}
	}
}
