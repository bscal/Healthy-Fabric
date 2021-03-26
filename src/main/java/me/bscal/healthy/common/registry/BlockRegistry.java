package me.bscal.healthy.common.registry;

import me.bscal.healthy.Healthy;
import me.bscal.healthy.common.blocks.UnlitTorch;
import me.bscal.healthy.common.blocks.UnlitWallTorch;
import me.bscal.healthy.common.util.ResourceGenerators;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JModel;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.devtech.arrp.api.RuntimeResourcePack.id;
import static net.devtech.arrp.json.blockstate.JState.*;
import static net.devtech.arrp.json.loot.JLootTable.*;
import static net.devtech.arrp.json.models.JModel.model;
import static net.devtech.arrp.json.models.JModel.textures;

public class BlockRegistry
{
	private static final String NAMESPACE = Healthy.MOD_ID + ":blocks";

	public static final Block UNLIT_TORCH = new UnlitTorch(
			FabricBlockSettings.of(Material.SUPPORTED));
	public static final Block UNLIT_WALL_TORCH = new UnlitWallTorch(
			FabricBlockSettings.of(Material.SUPPORTED));

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
