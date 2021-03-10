package me.bscal.healthy.common.config;

import com.oroarmor.config.Config;
import com.oroarmor.config.ConfigItem;
import com.oroarmor.config.ConfigItemGroup;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.util.List;

import static com.google.common.collect.ImmutableList.of;

public class HealthyConfig extends Config
{

	public static final ConfigItemGroup ROOT = new RootGroup();
	public static final List<ConfigItemGroup> CONFIGS = of(ROOT);
	public static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "healthy_config.json");

	public HealthyConfig()
	{
		super(CONFIGS, CONFIG_FILE, "healthy_config");
	}

	public static class RootGroup extends ConfigItemGroup
	{
		public static final ConfigItem<Boolean> HUNGER_OVERHAUL = new ConfigItem<Boolean>("hungerOverhaul", true, "Overhaul_hunger");

		public RootGroup()
		{
			super(of(HUNGER_OVERHAUL), "root");
		}
	}
}
