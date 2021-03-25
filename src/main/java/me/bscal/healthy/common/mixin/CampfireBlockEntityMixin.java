package me.bscal.healthy.common.mixin;

import me.bscal.healthy.Healthy;
import me.bscal.healthy.IBurnableCampfireBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(CampfireBlockEntity.class)
public abstract class CampfireBlockEntityMixin implements IBurnableCampfireBlockEntity
{

	/**
	 * Amount of ticks the fire should be lit
	 */
	private int m_fireLitTicks;

	/**
	 * Injections
	 */

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/CampfireBlockEntity;updateItemsBeingCooked()V"))
	public void tick(CallbackInfo cb)
	{
		CampfireBlockEntity blockEntity = (CampfireBlockEntity) (Object) this;
		if (!Objects.requireNonNull(blockEntity.getWorld()).isClient)
		{
			BlockState state = blockEntity.getCachedState();
			if (state.get(Properties.LIT))
			{
				m_fireLitTicks--;
				if (m_fireLitTicks < 0)
				{
					blockEntity.getWorld().setBlockState(blockEntity.getPos(), state.with(Properties.LIT, false), 11);
				}
			}
			else if (m_fireLitTicks > 0)
				m_fireLitTicks = 0;
		}
	}

	@Inject(method = "fromTag", at = @At(value = "RETURN"))
	public void fromTag(BlockState state, CompoundTag tag, CallbackInfo cb)
	{
		m_fireLitTicks = tag.getInt("FireLitTicks");
	}

	@Inject(method="toTag", at = @At(value = "TAIL"))
	public void toTag(CompoundTag tag, CallbackInfoReturnable<CompoundTag> cb)
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
}
