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

public class DropItemToggleExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Player player = (Player) src;
		Boolean toggle = ctx.<Boolean> getOne("toggle").get();

		ConfigManager.setConfigValue(new Object[] { "pvplogger", "toggle" }, toggle);

		player.sendMessage(Text.of(TextColors.RED, "[PvPLogger]: ", TextColors.GREEN, "Players will now drop their inventory if they disconnect during combat."));

		return CommandResult.success();
	}
}
