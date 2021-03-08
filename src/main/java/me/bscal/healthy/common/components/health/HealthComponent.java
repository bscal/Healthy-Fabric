package me.bscal.healthy.common.components.health;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;

public class HealthComponent implements IHealthComponent
{

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
		if (tag.contains("heal"))
		{
			m_heal = new Heal();
			m_heal.Read(tag);
		}


		m_canReceiveHealing = tag.getBoolean("canReceive");

	}

	@Override
	public void writeToNbt(CompoundTag tag)
	{
		if (m_heal != null && !m_heal.finished)
			m_heal.Write(tag);

		tag.putBoolean("canReceive", m_canReceiveHealing);
	}
}
