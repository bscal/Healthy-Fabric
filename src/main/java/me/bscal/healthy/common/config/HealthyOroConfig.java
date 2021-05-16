package me.bscal.healthy.common.config;

import com.oroarmor.config.ArrayConfigItem;
import com.oroarmor.config.Config;
import com.oroarmor.config.ConfigItem;
import com.oroarmor.config.ConfigItemGroup;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.util.List;

import static com.google.common.collect.ImmutableList.of;

public class HealthyOroConfig extends Config
{

	public static final ConfigItemGroup rootGroup = new ConfigGroupRoot();
	public static final List<ConfigItemGroup> configs = of(rootGroup);

	public HealthyOroConfig()
	{
		super(configs, new File(String.valueOf(
				new File(FabricLoader.getInstance().getConfigDir().toFile(), "healthy_config.json"))), "healthy_config");
	}

	public static class ConfigGroupRoot extends ConfigItemGroup
	{
		public static final ConfigItem<Integer> stickTicks = new ConfigItem<>("stickTicks", 20 * 10, "stickTicks");
		public static final ConfigItem<Double> stickIgnite = new ConfigItem<>("stickIgniteChance", .5, "stickIgniteChance");
		public static final ConfigItem<Integer> logTicks = new ConfigItem<>("logTicks", 20 * 120, "logTicks");
		public static final ConfigItem<Double> logIgnite = new ConfigItem<>("logIgniteChance", 0.0, "logIgniteChance");
		public static final ArrayConfigItem<String> burnableItems = new ArrayConfigItem<>("burnable_items",
				new String[]{"minecraft:stick", "minecraft:oak_log", "minecraft:spruce_log", "minecraft:birch_log",
				"minecraft:jungle_log", "minecraft:acacia_log", "minecraft:dark_oak_log", "minecraft:stripped_spruce_log", "minecraft:stripped_birch_log",
				"minecraft:stripped_jungle_log", "minecraft:stripped_acacia_log", "minecraft:stripped_dark_oak_log", "minecraft:stripped_oak_log"},
		"burnable_items");

		public ConfigGroupRoot() {
			super(of(stickTicks, stickIgnite, logTicks, logIgnite, burnableItems), "root");
		}
	}
}
