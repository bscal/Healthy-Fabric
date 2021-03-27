package me.bscal.healthy.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class UnlitWallTorch extends WallTorchBlock
{
	public UnlitWallTorch(AbstractBlock.Settings settings)
	{
		super(settings.noCollision().nonOpaque(), ParticleTypes.SMOKE);
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
	}
}
