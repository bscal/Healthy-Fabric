package me.bscal.healthy.common.mixin;

import me.bscal.healthy.Healthy;
import me.bscal.healthy.IBurnableCampfireBlockEntity;
import me.bscal.healthy.common.mixin_accessors.CampfireBlockEntityAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(CampfireBlockEntity.class)
public abstract class CampfireBlockEntityMixin extends BlockEntity implements Clearable, IBurnableCampfireBlockEntity,
		CampfireBlockEntityAccessor
{

	/**
	 * Amount of ticks the fire should be lit
	 */
	@Unique
	private int m_fireLitTicks;

	public CampfireBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state)
	{
		super(type, pos, state);
	}

	/**
	 * Injections
	 */

	@Inject(method = "litServerTick", at = @At(value = "HEAD"))
	private static void OnLitServerTick(World world, BlockPos pos, BlockState state,
			CampfireBlockEntity campfire, CallbackInfo ci)
	{
		CampfireBlockEntityAccessor campfireAccessor = (CampfireBlockEntityAccessor)campfire;
		if (!world.isClient())
		{
			int ticks = campfireAccessor.GetTicks() - 1;
			campfireAccessor.SetTicks(ticks--);
			if (ticks < 0)
			{
				world.setBlockState(campfire.getPos(), state.with(Properties.LIT, false), 11);
			}
		}
	}

	@Inject(method = "unlitServerTick", at = @At(value = "HEAD"))
	private static void OnUnlitServerTick(World world, BlockPos pos, BlockState state,
			CampfireBlockEntity campfire, CallbackInfo ci)
	{
		CampfireBlockEntityAccessor campfireAccessor = (CampfireBlockEntityAccessor)campfire;
		if (!world.isClient() && campfireAccessor.GetTicks() > 0)
		{
			campfireAccessor.SetTicks(0);
		}
	}

	// SNAPSHOT BROKE THIS
//	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/CampfireBlockEntity;updateItemsBeingCooked()V"))
//	public void tick(CallbackInfo cb)
//	{
//		CampfireBlockEntity blockEntity = (CampfireBlockEntity) (Object) this;
//		if (!Objects.requireNonNull(blockEntity.getWorld()).isClient)
//		{
//			BlockState state = blockEntity.getCachedState();
//			if (state.get(Properties.LIT))
//			{
//				m_fireLitTicks--;
//				if (m_fireLitTicks < 0)
//				{
//					blockEntity.getWorld().setBlockState(blockEntity.getPos(), state.with(Properties.LIT, false), 11);
//				}
//			}
//			else if (m_fireLitTicks > 0)
//				m_fireLitTicks = 0;
//		}
//	}

	@Inject(method = "readNbt", at = @At(value = "RETURN"))
	public void fromTag(NbtCompound tag, CallbackInfo cb)
	{
		m_fireLitTicks = tag.getInt("FireLitTicks");
	}

	@Inject(method="writeNbt", at = @At(value = "TAIL"))
	public void toTag(NbtCompound tag, CallbackInfoReturnable<NbtCompound> cb)
	{
		tag.putInt("FireLitTicks", m_fireLitTicks);
	}

	/**
	 * Override custom interface methods.
	 */

	@Override
	public boolean isLit()
	{
		return CampfireBlock.isLitCampfire(((CampfireBlockEntity) ((Object) this)).getCachedState());
	}

	@Override
	public ActionResult addFireTicks(Item item, int ticks)
	{
		CampfireBlockEntity blockEntity = (CampfireBlockEntity) (Object) this;
		if (!Objects.requireNonNull(blockEntity.getWorld()).isClient)
		{
			BlockState state = blockEntity.getCachedState();
			boolean lit = CampfireBlock.isLitCampfire(state);
			if (!state.get(Properties.WATERLOGGED) && lit)
			{
				m_fireLitTicks += ticks;
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.FAIL;
	}

	@Override
	public ActionResult tryFireIgnite(Item item, int ticks, float chance)
	{
		CampfireBlockEntity blockEntity = (CampfireBlockEntity) (Object) this;
		if (!Objects.requireNonNull(blockEntity.getWorld()).isClient)
		{
			BlockState state = blockEntity.getCachedState();
			boolean lit = CampfireBlock.isLitCampfire(state);
			if (!state.get(Properties.WATERLOGGED) && !lit && Healthy.RAND.nextFloat() > chance)
			{
				blockEntity.getWorld().setBlockState(blockEntity.getPos(), state.with(Properties.LIT, true), 11);
				m_fireLitTicks += ticks;
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.FAIL;
	}

	@Unique
	@Override
	public int GetTicks()
	{
		return m_fireLitTicks;
	}

	@Unique
	@Override
	public void SetTicks(int i)
	{
		m_fireLitTicks = i;
	}
}
