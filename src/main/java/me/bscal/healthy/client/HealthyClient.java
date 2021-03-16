package me.bscal.healthy.client;

import com.mojang.brigadier.context.CommandContext;
import me.bscal.healthy.Healthy;
import me.bscal.healthy.client.gui.HealthyGUI;
import me.bscal.healthy.client.gui.HealthyScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT) public class HealthyClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		//RegisterClient();
	}

	public void RegisterClient()
	{
		ClientCommandManager.DISPATCHER.register(ClientCommandManager.literal("healthy").executes(this::DefaultCommand));
		ClientCommandManager.DISPATCHER.register(ClientCommandManager.literal("ui").executes(this::UICommand));
	}

	private int DefaultCommand(CommandContext<FabricClientCommandSource> ctx)
	{
		Healthy.LOGGER.info("Client");
		return 1;
	}

	private int UICommand(CommandContext<FabricClientCommandSource> ctx)
	{
		if (ctx.getSource().getWorld().isClient)
		{
			MinecraftClient.getInstance().openScreen(new HealthyScreen(new HealthyGUI()));
		}

		return 1;
	}
}
