package me.bscal.healthy.common.components.health;

import me.bscal.healthy.Healthy;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;

public class HealthComponent implements IHealthComponent
{

	public static final String HP_REGEN_KEY = "regen_tag";

	private Heal m_heal;
	private boolean m_canReceiveHealing = true;

	@Override
	public void AddHealing(Heal heal)
	{
		if (m_canReceiveHealing)
		{
			m_heal = heal;
			m_canReceiveHealing = false;
		}
	}

	@Override
	public void UpdateHealth(LivingEntity entity)
	{
		if (m_heal != null && m_heal.CanUpdate(entity))
		{
			m_heal.Update(entity);

			if (m_heal.finished)
				m_canReceiveHealing = true;
		}
	}

	@Override
	public void SetHealing(Heal heal)
	{
		AddHealing(heal);
	}

	@Override
	public void StopHealing()
	{
		m_heal = Heal.ZERO;
		m_canReceiveHealing = true;
	}

	@Override
	public Heal GetHeal()
	{
		return m_heal;
	}

	@Override
	public boolean CanReceiveHealing()
	{
		return m_canReceiveHealing;
	}

	@Override
	public void readFromNbt(CompoundTag tag)
	{
		if (tag.contains(HP_REGEN_KEY))
		{
			Heal heal = new Heal();
			heal.Read((CompoundTag) tag.get(HP_REGEN_KEY));
			AddHealing(heal);
		}

	}

	@Override
	public void writeToNbt(CompoundTag tag)
	{
		if (m_heal != null && !m_heal.finished)
		{
			CompoundTag healTag = new CompoundTag();
			m_heal.Write(healTag);
			tag.put(HP_REGEN_KEY, healTag);
		}
	}
}
