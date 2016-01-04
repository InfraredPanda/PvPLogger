package io.github.infraredpanda.pvplogger.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.github.infraredpanda.pvplogger.utils.ConfigManager;

public class PunishmentExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Player player = (Player) src;
		String punishment = ctx.<String> getOne("punishment").get();

		ConfigManager.setConfigValue(new Object[] { "pvplogger", "punishment" }, punishment);

		player.sendMessage(Text.of(TextColors.RED, "[PvPLogger]: ", TextColors.GREEN, "Punishment set to: ", TextColors.GOLD, punishment));

		return CommandResult.success();
	}
}
