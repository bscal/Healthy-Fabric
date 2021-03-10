package me.bscal.healthy.common.commands;

import static net.minecraft.server.command.CommandManager.literal;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import me.bscal.healthy.Healthy;
import me.bscal.healthy.client.gui.HealthyGUI;
import me.bscal.healthy.client.gui.HealthyScreen;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Objects;

public class BasicCommands
{

	public void Register()
	{
		CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> {
			CommandNode<ServerCommandSource> healthyCommand = dispatcher.register(
					literal("healthy").executes(this::DefaultCommand));

			LiteralCommandNode<ServerCommandSource> build = literal("ui").executes(this::UICommand).build();

			healthyCommand.addChild(build);

		}));
	}

	public void RegisterServer(MinecraftServer server)
	{
		final boolean dedicated = server.isDedicated();
		final RootCommandNode<ServerCommandSource> root = server.getCommandManager()
				.getDispatcher()
				.getRoot();

		CommandNode<ServerCommandSource> healthyCommand = root.getChild("healthy");

		CommandNode<ServerCommandSource> healthyUICommand = root.getChild("ui");
		healthyCommand.addChild(healthyUICommand);

	}

	private int DefaultCommand(CommandContext<ServerCommandSource> serverCommandSourceCommandContext)
	{
		Healthy.LOGGER.info("TESTING");
		return 1;
	}

	private int UICommand(CommandContext<ServerCommandSource> ctx)
	{
		if (!ctx.getSource().getMinecraftServer().isDedicated())
		{
			MinecraftClient.getInstance().openScreen(new HealthyScreen(new HealthyGUI()));
		}

		return 1;
	}

}
