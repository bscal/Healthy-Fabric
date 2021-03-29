package me.bscal.healthy.common.mixin;

import me.bscal.healthy.Healthy;
import me.bscal.healthy.common.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(TorchBlock.class)
public abstract class TorchBlockMixin extends Block
{

	private static final IntProperty PROP = Properties.AGE_15;
	private static final int MIN_AGE = 1;

	public TorchBlockMixin(Settings settings)
	{
		super(settings);
		setDefaultState(getStateManager().getDefaultState().with(PROP, 0));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		super.appendProperties(builder);
		builder.add(PROP);
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
		//boolean isWallTorch = state.getBlock().is(Blocks.WALL_TORCH);
		if (state.getBlock().is(Blocks.TORCH))
		{
			int age = state.get(PROP);

			Healthy.LOGGER.info("AGE: " + age);

			if (age < MIN_AGE)
			{
				world.setBlockState(pos, state.with(PROP, age + 1));
			}
			else if ( Healthy.RAND.nextFloat() > .25f)
			{
				BlockState newState = BlockRegistry.UNLIT_TORCH.getDefaultState();
				//if (isWallTorch)
					//newState.with(WallTorchBlock.FACING, state.get(WallTorchBlock.FACING));
				world.setBlockState(pos, newState);
				world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_FIRE_EXTINGUISH,
						SoundCategory.NEUTRAL, .5f, 1.0f);
			}
		}
	}
}
