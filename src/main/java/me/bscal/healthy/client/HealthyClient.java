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
	}

}
