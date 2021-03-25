package me.bscal.healthy.common.config;

import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.api.ConfigGroup;

import java.util.Arrays;
import java.util.List;

public final class HealthyConfig implements ConfigGroup
{
	@Transitive @ConfigEntries public static class RootGroup implements ConfigGroup
	{
		public static boolean hungerOverhaul = true;

		@Transitive @ConfigEntries public static class CampfireItemsGroup implements ConfigGroup
		{
			public static int stickTicks = 20 * 10;
			public static float stickIgnite = .5f;
			public static int logTicks = 20 * 120;
			public static float logIgnite;
			public static List<String> burnableItems = Arrays.asList("minecraft:stick", "minecraft:oak_log", "minecraft:spruce_log", "minecraft:birch_log",
					"minecraft:jungle_log", "minecraft:acacia_log", "minecraft:dark_oak_log", "minecraft:stripped_spruce_log", "minecraft:stripped_birch_log",
					"minecraft:stripped_jungle_log", "minecraft:stripped_acacia_log", "minecraft:stripped_dark_oak_log", "minecraft:stripped_oak_log");

		}

	}
}
