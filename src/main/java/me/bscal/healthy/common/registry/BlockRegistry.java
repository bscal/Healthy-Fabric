package me.bscal.healthy.common.registry;

import me.bscal.healthy.Healthy;
import me.bscal.healthy.common.blocks.UnlitTorch;
import me.bscal.healthy.common.blocks.UnlitWallTorch;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockRegistry
{

	public static final Block UNLIT_TORCH = new UnlitTorch(
			FabricBlockSettings.of(Material.STRUCTURE_VOID));
	public static final Block UNLIT_WALL_TORCH = new UnlitWallTorch(
			FabricBlockSettings.of(Material.STRUCTURE_VOID));
	public static void Register()
	{
		Registry.register(Registry.BLOCK, new Identifier(Healthy.MOD_ID, "unlit_torch"), UNLIT_TORCH);
		Registry.register(Registry.BLOCK, new Identifier(Healthy.MOD_ID, "unlit_wall_torch"),
				UNLIT_WALL_TORCH);

		//((ResourceGenerators.Block) UNLIT_TORCH).client(Healthy.RESOURCE_PACK, new Identifier(Healthy.MOD_ID, "unlit_torch"));
		//((ResourceGenerators.Block) UNLIT_WALL_TORCH).client(Healthy.RESOURCE_PACK, new Identifier(Healthy.MOD_ID, "unlit_wall_torch"));

		//		Healthy.RESOURCE_PACK.addLootTable(id(Healthy.MOD_ID, "unlit_torch"),
		//				loot(NAMESPACE).pool(pool().rolls(1)
		//						.entry(entry().type(NAMESPACE))
		//						.condition(predicate("minecraft:survives_explosion"))));
	}

}
