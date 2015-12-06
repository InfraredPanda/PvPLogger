package io.github.infraredpanda.pvplogger.listener;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;


import io.github.infraredpanda.pvplogger.PvPLogger;

public class PlayerDamageListener
{
	@Listener
	public void onPlayerDamaged(DamageEntityEvent event)
	{
		// if Player Damaged
		if (event.getTargetEntity() instanceof Player)
		{
			Player victim = (Player) event.getTargetEntity();
			victim.sendMessage(Texts.of(TextColors.DARK_RED, "[PvPLogger]: ", TextColors.RED, "You are engaged in combat! Do not logout for ten seconds or you will be punished!"));

			Scheduler scheduler = PvPLogger.game.getScheduler();
			Task.Builder taskBuilder = scheduler.createTaskBuilder();

			if (PvPLogger.playersTakenDamage.contains(victim.getUniqueId()))
			{
				Set<Task> taskToRemove = scheduler.getTasksByName("PvPLogger - Remove Victim " + victim.getUniqueId().toString());

				for (Task task : taskToRemove)
				{
					scheduler.getScheduledTasks().remove(task);
				}
			}
			else
			{
				PvPLogger.playersTakenDamage.add(victim.getUniqueId());
			}

			taskBuilder.execute(() -> {
				victim.sendMessage(Texts.of(TextColors.DARK_RED, "[PvPLogger]: ", TextColors.GREEN, "You are no longer in combat, you may disconnect safely."));
				PvPLogger.playersTakenDamage.remove(victim.getUniqueId());
			}).delay(10, TimeUnit.SECONDS).name("PvPLogger - Remove Victim " + victim.getUniqueId().toString()).submit(PvPLogger.game.getPluginManager().getPlugin("PvPLogger").get().getInstance());
		}
	}
}
