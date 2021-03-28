package me.bscal.healthy.common.mixin;

import me.bscal.healthy.Healthy;
import me.bscal.healthy.common.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(TorchBlock.class)
public abstract class TorchBlockMixin extends Block
{

	private static final int MIN_AGE = 14;

	public TorchBlockMixin(Settings settings)
	{
		super(settings);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		super.appendProperties(builder);
		builder.add(Properties.AGE_15);
	}

	@Override
	public boolean hasRandomTicks(BlockState state)
	{
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
	{
		boolean isWallTorch = state.getBlock().is(Blocks.WALL_TORCH);
		if (isWallTorch || state.getBlock().is(Blocks.TORCH))
		{
			int age = state.get(Properties.AGE_15);

			if (!world.isClient)
				state.with(Properties.AGE_15, age + 1);

			Healthy.LOGGER.info("AGE: " + age);

			if (age > MIN_AGE && Healthy.RAND.nextFloat() > .25f)
			{
				BlockState newState = BlockRegistry.UNLIT_TORCH.getDefaultState();
				if (isWallTorch)
					newState.with(WallTorchBlock.FACING, state.get(WallTorchBlock.FACING));
				world.setBlockState(pos, newState);
				world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_FIRE_EXTINGUISH,
						SoundCategory.AMBIENT, 0f, 0f, true);
			}
		}
	}
}
