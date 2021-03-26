package me.bscal.healthy.common.registry;

import me.bscal.healthy.Healthy;
import me.bscal.healthy.common.components.injuries.InjuryRegistry;
import me.bscal.healthy.common.items.Bandage;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegistry
{

	public static final Item BANDAGE = new Bandage(new FabricItemSettings().group(ItemGroup.MISC));

	public static final Item UNLIT_TORCH = new WallStandingBlockItem(BlockRegistry.UNLIT_TORCH, BlockRegistry.UNLIT_WALL_TORCH, new FabricItemSettings().group(ItemGroup.DECORATIONS));

	public static void Register()
	{
		Registry.register(Registry.ITEM, new Identifier(Healthy.MOD_ID, "bandage"), BANDAGE);
		Registry.register(Registry.ITEM, new Identifier(Healthy.MOD_ID, "unlit_torch"), UNLIT_TORCH);
	}

}
