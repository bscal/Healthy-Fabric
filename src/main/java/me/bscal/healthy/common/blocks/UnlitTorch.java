package me.bscal.healthy.common.blocks;

import me.bscal.healthy.ResourceGen;
import me.bscal.healthy.common.util.ResourceGenerators;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import static me.bscal.healthy.ResourceGen.prefixPath;
import static net.devtech.arrp.json.blockstate.JState.*;
import static net.devtech.arrp.json.models.JModel.model;
import static net.devtech.arrp.json.models.JModel.textures;

public class UnlitTorch extends TorchBlock implements ResourceGenerators.Block
{
	public UnlitTorch(Settings settings)
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
		pack.addModel(JModel.model("items/generate"), prefixPath(id, "block"));
	}

	@Override
	public void generateBlockModel(RuntimeResourcePack pack, Identifier id)
	{
		Identifier prefix = ResourceGen.prefixPath(id, "block");
		pack.addModel(model("block/template_torch").textures(textures().var("torch", prefix.toString())), prefix);
	}

	@Override
	public void generateBlockState(RuntimeResourcePack pack, Identifier id)
	{
		pack.addBlockState(state(variant(JState.model(prefixPath(id, "block").toString()))), id);
	}
}
