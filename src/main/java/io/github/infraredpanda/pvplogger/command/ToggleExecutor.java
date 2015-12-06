package io.github.infraredpanda.pvplogger.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import io.github.infraredpanda.pvplogger.PvPLogger;

public class ToggleExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		boolean toggle = ctx.<Boolean> getOne("toggle").get();
		PvPLogger.toggle = toggle;
		
		if (toggle)
			src.sendMessage(Texts.of(TextColors.GREEN, "PvP Logger is now on!"));
		else
			src.sendMessage(Texts.of(TextColors.RED, "PvP Logger is now off!"));

		return CommandResult.success();
	}
}
