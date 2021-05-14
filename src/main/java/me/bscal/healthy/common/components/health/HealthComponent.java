package me.bscal.healthy.common.components.health;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class HealthComponent implements IHealthComponent, AutoSyncedComponent
{

	public static final String HP_REGEN_KEY = "regen_tag";

	private final PlayerEntity provider;
	private Heal m_heal = Heal.ZERO;
	private boolean m_canReceiveNewHeal;

	private float m_healingModifier = 1.0f;

	public HealthComponent(PlayerEntity provider)
	{
		this.provider = provider;
	}

	@Override
	public void AddHealing(Heal heal)
	{
		if (!provider.getEntityWorld().isClient && m_canReceiveNewHeal)
		{
			m_heal = heal;
			m_canReceiveNewHeal = false;
			HealthProvider.HEALTH.sync(provider);
		}
	}

	@Override
	public void UpdateHealth(LivingEntity entity)
	{
		if (m_heal == null)
			return;

		if (m_heal.CanUpdate(entity))
		{
			m_heal.Update(entity);

			entity.heal(m_heal.healingPerUpdate * Math.max(0, m_healingModifier));

			HealthProvider.HEALTH.sync(provider);
		}

		 if (m_heal.finished)
		 {
			 m_canReceiveNewHeal = true;
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
		m_canReceiveNewHeal = true;
	}

	@Override
	public Heal GetHeal()
	{
		return m_heal;
	}

	@Override
	public boolean CanReceiveHealing()
	{
		return m_canReceiveNewHeal;
	}

	@Override
	public void SetHealingModifier(float mod)
	{
		m_healingModifier = mod;
	}

	@Override
	public float GetHealingModifier()
	{
		return m_healingModifier;
	}

	@Override
	public void readFromNbt(NbtCompound tag)
	{
		m_canReceiveNewHeal = tag.getBoolean("canReceiveNewHeal");
		m_heal.Read((NbtCompound) tag.get(HP_REGEN_KEY));
	}

	@Override
	public void writeToNbt(NbtCompound tag)
	{
		NbtCompound healTag = new NbtCompound();
		healTag.putBoolean("canReceiveNewHeal", m_canReceiveNewHeal);
		m_heal.Write(healTag);
		tag.put(HP_REGEN_KEY, healTag);
	}
}
