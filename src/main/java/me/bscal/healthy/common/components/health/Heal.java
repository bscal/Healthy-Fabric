package me.bscal.healthy.common.components.health;

import me.bscal.healthy.Healthy;
import me.bscal.healthy.common.components.buff.AbstractBuff;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;

public class Heal extends AbstractBuff
{

	public static final Heal ZERO = CreateHeal("ZERO");

	public int duration;
	public int remainingDuration;
	public int ticksPerUpdate;
	public boolean finished;
	public float totalHealing;
	public float healingPerUpdate;

	public Heal(String name, int id)
	{
		super(name, id);
	}

	public static Heal CreateHeal(String name)
	{
		return new Heal(name, 0);
	}

	public Heal SetHealing(float totalHealing, int durInTicks, int ticksPerHeal)
	{
		this.duration = durInTicks;
		this.remainingDuration = durInTicks;
		this.ticksPerUpdate = ticksPerHeal;
		this.totalHealing = totalHealing;
		this.healingPerUpdate = totalHealing / (float) (durInTicks / ticksPerHeal);
		return this;
	}

	@Override
	public int GetDuration()
	{
		return duration;
	}

	@Override
	public int GetRemainingDuration()
	{
		return remainingDuration;
	}

	@Override
	public int GetTicksPerUpdate()
	{
		return ticksPerUpdate;
	}

	@Override
	public boolean IsGood()
	{
		return true;
	}

	@Override
	public boolean IsFinished()
	{
		return finished;
	}

	@Override
	public boolean CanUpdate(LivingEntity entity)
	{
		return !finished && Healthy.GetServer().getTicks() % Math.max(1, ticksPerUpdate) == 0;
	}

	@Override
	public void Update(LivingEntity entity)
	{
		if (!finished)
		{
			entity.heal(healingPerUpdate);
			UpdateDuration(entity);
		}

		if (remainingDuration <= 0)
			finished = true;
	}

	@Override
	public void UpdateDuration(LivingEntity entity)
	{
		remainingDuration -= ticksPerUpdate;
	}

	@Override
	public void Write(CompoundTag tag)
	{
		super.Write(tag);
		duration = tag.getInt("duration");
		remainingDuration = tag.getInt("remainingDuration");
		ticksPerUpdate = tag.getInt("ticksPerUpdate");
		totalHealing = tag.getFloat("totalHealing");
		healingPerUpdate = tag.getFloat("healingPerUpdate");
	}

	@Override
	public void Read(CompoundTag tag)
	{
		super.Read(tag);
		tag.putInt("duration", duration);
		tag.putInt("remainingDuration", remainingDuration);
		tag.putInt("ticksPerUpdate", ticksPerUpdate);
		tag.putFloat("totalHealing", totalHealing);
		tag.putFloat("healingPerUpdate", healingPerUpdate);
	}
}
