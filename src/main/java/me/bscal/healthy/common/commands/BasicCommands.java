package me.bscal.healthy.common.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.bscal.healthy.Healthy;
import me.bscal.healthy.client.gui.HealthyGUI;
import me.bscal.healthy.client.gui.HealthyScreen;
import me.bscal.healthy.common.components.injuries.InjuryProvider;
import me.bscal.healthy.common.components.injuries.InjuryRegistry;
import me.bscal.healthy.common.components.injuries.injurytypes.Bleed;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.util.Objects;

import static net.minecraft.server.command.CommandManager.literal;

public class BasicCommands
{

	public void Register()
	{
		CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> {
			CommandNode<ServerCommandSource> healthyCommand = dispatcher.register(
					literal("healthy").executes(this::DefaultCommand));

			if (!dedicated)
			{
				LiteralCommandNode<ServerCommandSource> healCommand = literal("heal").executes(
						this::HealCommand).build();
				healthyCommand.addChild(healCommand);
				LiteralCommandNode<ServerCommandSource> pDebugCommand = literal("pdebug").executes(
						this::PDebugCommand).build();
				healthyCommand.addChild(pDebugCommand);
				LiteralCommandNode<ServerCommandSource> fixCommand = literal("fix").executes(
						this::fixCommand).build();
				healthyCommand.addChild(fixCommand);
				LiteralCommandNode<ServerCommandSource> uiCommand = literal("ui").executes(
						this::UICommand).build();
				healthyCommand.addChild(uiCommand);
				LiteralCommandNode<ServerCommandSource> bleedCommand = literal("bleed").executes(
						this::BleedCommand).build();
				healthyCommand.addChild(bleedCommand);
			}

		}));
	}

	/**
	 * Attempts to reset players
	 */
	private int fixCommand(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException
	{
		if (!ctx.getSource().getMinecraftServer().isDedicated() && !ctx.getSource()
				.getWorld().isClient)
		{
			PlayerEntity player = ctx.getSource().getPlayer();
			Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).clearModifiers();
		}

		return 1;
	}

	private int BleedCommand(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException
	{
		if (!ctx.getSource().getMinecraftServer().isDedicated() && !ctx.getSource()
				.getWorld().isClient)
		{
			PlayerEntity player = ctx.getSource().getPlayer();
			Bleed bleed = (Bleed)InjuryRegistry.BLEED_TYPE.MakeNew(player);
			bleed.SetAttacker(player);
			InjuryProvider.INJURY.get(player).AddInjury(bleed, true);
			InjuryProvider.INJURY.get(player).AddInjury(InjuryRegistry.HEAVY_BLEED_TYPE.MakeNew(player), true);
			InjuryProvider.INJURY.get(player).AddInjury(InjuryRegistry.INFECTION_TYPE.MakeNew(player), true);
			Healthy.LOGGER.info("COMMAND");
		}

		return 1;
	}

	private int DefaultCommand(CommandContext<ServerCommandSource> ctx)
	{
		return 1;
	}

	private int UICommand(CommandContext<ServerCommandSource> ctx)
	{
		if (!ctx.getSource()
				.getMinecraftServer()
				.isDedicated() && MinecraftClient.getInstance().currentScreen == null)
		{
			MinecraftClient.getInstance().openScreen(new HealthyScreen(new HealthyGUI()));
		}
		return 1;
	}

	private int PDebugCommand(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException
	{
		if (!ctx.getSource().getMinecraftServer().isDedicated() && !ctx.getSource()
				.getWorld().isClient)
		{
			ServerPlayerEntity p = ctx.getSource().getPlayer();
			HungerManager hm = p.getHungerManager();
			p.sendMessage(new LiteralText(
					String.format("HP: %.3f/%.3f (%.3f)", p.getHealth(), p.getMaxHealth(),
							p.getAbsorptionAmount())), false);
			p.sendMessage(new LiteralText(String.format("Food: %d | Sat: %.3f", hm.getFoodLevel(),
					hm.getSaturationLevel())), false);

			Healthy.LOGGER.info(
					String.format("HP: %.3f/%.3f (%.3f)", p.getHealth(), p.getMaxHealth(),
							p.getAbsorptionAmount()));
			Healthy.LOGGER.info(String.format("Food: %d | Sat: %.3f", hm.getFoodLevel(),
					hm.getSaturationLevel()));
		}

		return 1;
	}

	private int HealCommand(CommandContext<ServerCommandSource> ctx)
	{
		if (!ctx.getSource().getWorld().isClient)
		{
			try
			{
				ServerPlayerEntity player = ctx.getSource().getPlayer();
				player.setHealth(player.getMaxHealth());
			}
			catch (CommandSyntaxException e)
			{
				e.printStackTrace();
			}
		}

		return 1;
	}

}
