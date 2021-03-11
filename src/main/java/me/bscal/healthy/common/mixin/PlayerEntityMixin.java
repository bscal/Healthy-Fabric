package me.bscal.healthy.common.mixin;

import me.bscal.healthy.common.events.callbacks.PlayerSleepCallback;
import me.bscal.healthy.common.events.callbacks.PlayerTickCallback;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin
{
	/**
	 * Overrides PlayerEntity:canFoodHeal to return default impl and if heal is below 25% of max hp.
	 */
	@Inject(method = "canFoodHeal()Z", at = @At("HEAD"), cancellable = true)
	public void canFoodHeal(CallbackInfoReturnable<Boolean> cir)
	{
		PlayerEntity player = (PlayerEntity) (Object) this;

		boolean flag = player.getHealth() > 0.0F && player.getHealth() < player.getMaxHealth() && player
				.getHealth() <= 20 - player.getMaxHealth() * .75f;

		cir.setReturnValue(flag);
	}

	@Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;update(Lnet/minecraft/entity/player/PlayerEntity;)V"))
	public void update(HungerManager hungerManager, PlayerEntity player)
	{
		Difficulty difficulty = player.world.getDifficulty();

		// Reference to accessor for HungerManager so we can edit its private fields.
		// This should be safe since this is copied from minecraft's HungerManager update.
		HungerManagerAccessor hungerAccessor = (HungerManagerAccessor) hungerManager;
		// accessor fields
		final float exhaustion = hungerAccessor.GetExhaustion();
		final int foodStarvationTimer = hungerAccessor.GetFoodStarvationTimer();
		// manager fields
		final int foodLevel = hungerManager.getFoodLevel();
		final float saturationLevel = hungerManager.getSaturationLevel();

		// tick values
		final float exhaustionPerUpdate = 4.0f;
		final float saturationValue = 6.0f;
		final float saturationTimer = 10.0f;
		final float starvationTimer = 80.0f;
		final float starveDamage = 1.0f;
		final float foodExhaustionValue = 6.0f;

		hungerAccessor.SetPrevFoodLevel(hungerManager.getFoodLevel());
		if (exhaustion > 4.0F)
		{
			hungerAccessor.SetExhaustion(exhaustion - 4.0f);
			if (saturationLevel > 0.0F)
			{
				hungerAccessor.SetFoodSaturationLevel(Math.max(saturationLevel - 1.0F, 0.0F));
			}
			else if (difficulty != Difficulty.PEACEFUL)
			{
				hungerManager.setFoodLevel(Math.max(foodLevel - 1, 0));
			}
		}

		boolean bl = player.world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);

		if (bl && saturationLevel > 0.0F && player.canFoodHeal() && foodLevel >= 20)
		{
			hungerAccessor.SetFoodStarvationTimer(foodStarvationTimer + 1);
			if (foodStarvationTimer >= 10)
			{
				float f = Math.min(saturationLevel, 6.0F);
				player.heal(f / 6.0F);
				hungerManager.addExhaustion(f);
				hungerAccessor.SetFoodStarvationTimer(0);
			}
		}
		else if (bl && foodLevel >= 18 && player.canFoodHeal())
		{
			hungerAccessor.SetFoodStarvationTimer(foodStarvationTimer + 1);
			if (foodStarvationTimer >= 80)
			{
				player.heal(1.0F);
				hungerManager.addExhaustion(6.0F);
				hungerAccessor.SetFoodStarvationTimer(0);
			}
		}
		else if (foodLevel <= 0)
		{
			hungerAccessor.SetFoodStarvationTimer(foodStarvationTimer + 1);
			if (foodStarvationTimer >= 80)
			{
				if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL)
				{
					player.damage(DamageSource.STARVE, 1.0F);
				}
				hungerAccessor.SetFoodStarvationTimer(0);
			}
		}
		else
		{
			hungerAccessor.SetFoodStarvationTimer(0);
		}
	}

	/*
	 *  ******************************
	 *  * Custom PlayerEntity Events *
	 *  ******************************
	 */

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;updateWaterSubmersionState()Z"), method = "tick", cancellable = false)
	public void tick(CallbackInfo cb)
	{
		PlayerEntity player = (PlayerEntity) (Object) this;
		PlayerTickCallback.EVENT.invoker().tick(player);
	}

	@Inject(method = "wakeUp(ZZ)V", at = @At(value = "HEAD"), cancellable = true)
	public void wakeUp(boolean bl, boolean updateSleepingPlayers, CallbackInfo cb)
	{
		PlayerEntity player = (PlayerEntity) (Object) this;
		ActionResult result = PlayerSleepCallback.EVENT.invoker().onSleep(player, player.world);
		if (result == ActionResult.FAIL)
		{
			cb.cancel();
		}
	}

}
