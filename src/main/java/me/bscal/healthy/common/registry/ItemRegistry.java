package me.bscal.healthy.common.registry;

import me.bscal.healthy.Healthy;
import me.bscal.healthy.common.items.Bandage;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegistry
{

	public static final Item BANDAGE = new Bandage(new FabricItemSettings().group(ItemGroup.MISC));

	public static void Register()
	{
		Registry.register(Registry.ITEM, new Identifier(Healthy.MOD_ID, "bandage"), BANDAGE);
	}

}
