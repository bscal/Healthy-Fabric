package me.bscal.healthy.common.components.health;

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
	public boolean CanConsume(int currentTickCount)
	{
		return m_heal != null && currentTickCount % m_heal.ticksPerUpdate == 0 && m_heal.durationInTicks > 0 && !m_heal.finished;
	}

	@Override
	public float ConsumeHealing()
	{
		float healing = 0;

		healing += m_heal.Consume();

		if (m_heal.finished)
			m_canReceiveHealing = true;

		return healing;
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
		m_heal = new Heal();
		m_heal.totalHealing = tag.getFloat("totalHealing");
		m_heal.healingPerUpdate = tag.getFloat("healingPerUpdate");
		m_heal.durationInTicks = tag.getInt("duration");
		m_heal.ticksPerUpdate = tag.getInt("ticksPerUpdate");
		m_canReceiveHealing = tag.getBoolean("canReceive");
	}

	@Override
	public void writeToNbt(CompoundTag tag)
	{
		if (m_heal == null || m_heal.finished)
			return;

		tag.putFloat("totalHealing", m_heal.totalHealing);
		tag.putFloat("healingPerUpdate", m_heal.healingPerUpdate);
		tag.putInt("duration", m_heal.durationInTicks);
		tag.putInt("ticksPerUpdate", m_heal.ticksPerUpdate);
		tag.putBoolean("canReceive", m_canReceiveHealing);
	}

}
