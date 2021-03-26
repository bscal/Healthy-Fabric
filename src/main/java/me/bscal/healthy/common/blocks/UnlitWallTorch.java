package me.bscal.healthy.common.blocks;

import me.bscal.healthy.Healthy;
import me.bscal.healthy.ResourceGen;
import me.bscal.healthy.common.util.ResourceGenerators;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import static me.bscal.healthy.ResourceGen.prefixPath;
import static net.devtech.arrp.json.blockstate.JState.*;
import static net.devtech.arrp.json.models.JModel.model;
import static net.devtech.arrp.json.models.JModel.textures;

public class UnlitWallTorch extends WallTorchBlock implements ResourceGenerators.Block
{
	public UnlitWallTorch(AbstractBlock.Settings settings)
	{
		super(settings.noCollision().nonOpaque(), ParticleTypes.SMOKE);
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
	}

	@Override
	public void generateItemModel(RuntimeResourcePack pack, Identifier id)
	{
		Identifier newId = new Identifier(Healthy.MOD_ID, "unlit_torch");
		pack.addModel(JModel.model("item/generate"), prefixPath(newId, "block"));
	}

	@Override
	public void generateBlockModel(RuntimeResourcePack pack, Identifier id)
	{
		Identifier prefix = ResourceGen.prefixPath(new Identifier(Healthy.MOD_ID, "unlit_torch"), "block");
		pack.addModel(model("minecraft:block/template_torch_wall").textures(textures().var("torch", prefix.toString())), prefix);
	}

	@Override
	public void generateBlockState(RuntimeResourcePack pack, Identifier id)
	{
		Identifier newId = new Identifier(Healthy.MOD_ID, "unlit_wall_torch");
		pack.addBlockState(state(multipart(JState.model(prefixPath(newId, "block").toString())).when(when().add("facing", "north")),
				multipart(JState.model(prefixPath(newId, "block").toString()).y(90)).when(when().add("facing", "east")),
				multipart(JState.model(prefixPath(newId, "block").toString()).y(180)).when(when().add("facing", "south")),
				multipart(JState.model(prefixPath(newId, "block").toString()).y(270)).when(when().add("west", "west"))), id);
	}
}
