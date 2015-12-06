package io.github.infraredpanda.pvplogger.utils;

import java.io.IOException;
import java.util.Optional;

import io.github.infraredpanda.pvplogger.PvPLogger;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ConfigManager
{
	public static Optional<Object> getConfigValue(String path)
	{
		ConfigurationNode valueNode = PvPLogger.config.getNode((Object[]) (path).split("\\."));

		if (valueNode.getValue() != null)
		{
			return Optional.of(valueNode.getValue());
		}
		else
		{
			return Optional.empty();
		}
	}
	
	public static void setConfigValue(Object[] path, Object value)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = PvPLogger.getConfigManager();
		PvPLogger.config.getNode(path).setValue(value);

		try
		{
			configManager.save(PvPLogger.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[PvPLogger]: Failed to update config.");
		}
	}
}
