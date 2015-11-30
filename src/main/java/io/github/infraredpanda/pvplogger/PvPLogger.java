package io.github.infraredpanda.pvplogger;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.args.GenericArguments;
import org.spongepowered.api.util.command.spec.CommandSpec;

import com.google.inject.Inject;

import io.github.infraredpanda.pvplogger.command.PvPLoggerExecutor;

@Plugin(id = "PvPLogger", name = "PvPLogger", version = "0.1")
public class PvPLogger
{
	public static Game game;

	@Inject
	private Logger logger;

	public Logger getLogger()
	{
		return logger;
	}

	@Listener
	public void onServerPreInit(GamePreInitializationEvent event)
	{
		getLogger().info("PvPLogger loading...");
	}

	@Listener
	public void onServerInit(GameInitializationEvent event)
	{
		game = event.getGame();

		CommandSpec pvpLoggerCommandSpec = CommandSpec.builder()
			.description(Texts.of("PvPLogger Toggle"))
			.permission("pvplogger.use")
			.arguments(GenericArguments.onlyOne(GenericArguments.bool(Texts.of("toggle"))))
			.executor(new PvPLoggerExecutor())
			.build();
		
		game.getCommandDispatcher().register(this, pvpLoggerCommandSpec, "pvpl", "pvplogger");
	}

	@Listener
	public void onServerPostInit(GamePostInitializationEvent event)
	{
		getLogger().info("PvPLogger loaded!");
	}
}
