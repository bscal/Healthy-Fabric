package me.bscal.healthy.common.mixin;

import net.minecraft.entity.player.HungerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HungerManager.class)
public interface HungerManagerAccessor
{

	@Accessor("foodSaturationLevel")
	public void SetFoodSaturationLevel(float foodSaturationLevel);

	@Accessor("prevFoodLevel")
	public void SetPrevFoodLevel(int prevFoodLevel);

	@Accessor("prevFoodLevel")
	public int GetPrevFoodLevel();

	@Accessor("foodStarvationTimer")
	public void SetFoodStarvationTimer(int foodStarvationTimer);

	@Accessor("foodStarvationTimer")
	public int GetFoodStarvationTimer();

	@Accessor("exhaustion")
	public void SetExhaustion(float exhaustion);

	@Accessor("exhaustion")
	public float GetExhaustion();

}
