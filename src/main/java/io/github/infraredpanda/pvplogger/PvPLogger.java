package io.github.infraredpanda.pvplogger;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Texts;

import com.google.common.collect.Sets;
import com.google.inject.Inject;

import io.github.infraredpanda.pvplogger.command.ToggleExecutor;
import io.github.infraredpanda.pvplogger.listener.ClientDisconnectListener;
import io.github.infraredpanda.pvplogger.listener.ClientJoinListener;
import io.github.infraredpanda.pvplogger.listener.PlayerDamageListener;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Plugin(id = "PvPLogger", name = "PvPLogger", version = "0.1")
public class PvPLogger
{
	public static Game game;
	public static boolean toggle;
	public static Set<UUID> playersTakenDamage = Sets.newHashSet();
	public static Set<UUID> playersToPunish = Sets.newHashSet();
	public static ConfigurationNode config;
	public static ConfigurationLoader<CommentedConfigurationNode> configurationManager;

	@Inject
	private Logger logger;

	public Logger getLogger()
	{
		return logger;
	}

	@Inject
	@DefaultConfig(sharedRoot = true)
	private File dConfig;

	@Inject
	@DefaultConfig(sharedRoot = true)
	private ConfigurationLoader<CommentedConfigurationNode> confManager;

	@Listener
	public void onServerPreInit(GamePreInitializationEvent event)
	{
		getLogger().info("PvPLogger loading...");
		getLogger().info("Make sure to toggle PvPLogger on!");
	}

	@Listener
	public void onServerInit(GameInitializationEvent event)
	{
		game = event.getGame();

		// Config File Loading
		try
		{
			if (!dConfig.exists())
			{
				dConfig.createNewFile();
				config = confManager.load();
				config.getNode("pvplogger", "punishment").setValue("minecraft:kill @p");
				confManager.save(config);
			}

			configurationManager = confManager;
			config = confManager.load();
		}
		catch (IOException exception)
		{
			getLogger().error("The default configuration could not be loaded or created!");
		}

		CommandSpec pvpLoggerCommandSpec = CommandSpec.builder().description(Texts.of("PvPLogger Toggle")).permission("pvplogger.use").arguments(GenericArguments.onlyOne(GenericArguments.bool(Texts.of("toggle")))).executor(new ToggleExecutor()).build();

		game.getCommandManager().register(this, pvpLoggerCommandSpec, "pvpl", "pvplogger");

		game.getEventManager().registerListeners(this, new ClientDisconnectListener());
		game.getEventManager().registerListeners(this, new PlayerDamageListener());
		game.getEventManager().registerListeners(this, new ClientJoinListener());
	}

	@Listener
	public void onServerPostInit(GamePostInitializationEvent event)
	{
		getLogger().info("PvPLogger loaded!");
	}

	public static ConfigurationLoader<CommentedConfigurationNode> getConfigManager()
	{
		return configurationManager;
	}
}
