package io.github.infraredpanda.pvplogger.listener;

import java.util.Optional;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.github.infraredpanda.pvplogger.PvPLogger;
import io.github.infraredpanda.pvplogger.utils.ConfigManager;

public class ClientDisconnectListener
{
	@Listener
	public void onPlayerDisconnect(ClientConnectionEvent.Disconnect event)
	{
		Player disconnectingPlayer = event.getTargetEntity();

		if (PvPLogger.playersTakenDamage.contains(disconnectingPlayer.getUniqueId()))
		{
			PvPLogger.playersToPunish.add(disconnectingPlayer.getUniqueId());
			
			if((Boolean) ConfigManager.getConfigValue("pvplogger.toggle").orElse(false))
			{
				disconnectingPlayer.getInventory().slots().forEach(slot -> {
					Optional<ItemStack> item = slot.poll();

					if(item.isPresent())
					{
						ItemStack stack = item.get();
						Optional<Entity> entityItem = disconnectingPlayer.getWorld().createEntity(EntityTypes.ITEM, disconnectingPlayer.getLocation().getPosition());

						if(entityItem.isPresent())
						{
							Item entity = (Item) entityItem.get();
							entity.offer(Keys.REPRESENTED_ITEM, stack.createSnapshot());
						}
					}
					disconnectingPlayer.getInventory().clear();
				});
			}

			for (Player player : PvPLogger.game.getServer().getOnlinePlayers())
			{
				player.sendMessage(Text.of(TextColors.DARK_RED, "[PvPLogger]: ", TextColors.RED, disconnectingPlayer.getName() + " has disconnected during combat and will be punished when they return!"));
			}
		}
	}
}
