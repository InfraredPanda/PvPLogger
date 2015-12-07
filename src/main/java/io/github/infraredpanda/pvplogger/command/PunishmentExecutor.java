package io.github.infraredpanda.pvplogger.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

import io.github.infraredpanda.pvplogger.utils.ConfigManager;

public class PunishmentExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String punishment = ctx.<String> getOne("punishment").get();
		
		ConfigManager.setConfigValue(new Object[]{"pvplogger", "punishment"}, punishment);
		
		return CommandResult.success();
	}
}
