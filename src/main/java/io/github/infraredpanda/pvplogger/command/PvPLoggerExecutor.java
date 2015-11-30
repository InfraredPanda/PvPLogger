package io.github.infraredpanda.pvplogger.command;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import io.github.infraredpanda.pvplogger.PvPLogger;

public class PvPLoggerExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		boolean toggle = ctx.<Boolean> getOne("toggle").get();
		
		if (src instanceof Player)
		{
			Player player = (Player) src;
			
			for(Player p : PvPLogger.game.getServer().getOnlinePlayers())
			{
				toggle = true;
				p.sendMessage(Texts.of(TextColors.RED, "PvP Logger is now on!"));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.RED, "PvPLogger is now Active!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /pvplogger!"));
		}

		return CommandResult.success();
	}
}
