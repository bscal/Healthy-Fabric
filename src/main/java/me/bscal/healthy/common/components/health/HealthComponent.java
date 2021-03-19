package me.bscal.healthy.common.components.health;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import me.bscal.healthy.Healthy;
import me.bscal.healthy.common.components.buff.IBuff;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HealthComponent implements IHealthComponent, AutoSyncedComponent
{

	public static final String HP_REGEN_KEY = "regen_tag";

	private final PlayerEntity provider;
	private Heal m_heal = Heal.ZERO;
	private boolean m_canReceiveHealing = true;

	private final List<IBuff> m_buffs = new ArrayList<>();

	public HealthComponent(PlayerEntity provider)
	{
		this.provider = provider;
	}

	@Override
	public void AddHealing(Heal heal)
	{
		if (!provider.getEntityWorld().isClient && m_canReceiveHealing)
		{
			m_heal = heal;
			m_canReceiveHealing = false;
			HealthProvider.HEALTH.sync(provider);
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

			HealthProvider.HEALTH.sync(provider);
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
	public void AddBuff(IBuff buff)
	{
		if (buff != null && !buff.IsFinished())
		{
			m_buffs.add(buff);
		}
	}

	@Override
	public List<IBuff> GetBuffs()
	{
		return m_buffs;
	}

	@Override
	public IBuff[] GetBuff(String name)
	{
		return m_buffs.stream().filter((buff) -> buff.GetName().equals(name)).toArray(IBuff[]::new);
	}

	@Override
	public Optional<IBuff> GetByKey(String key)
	{
		for (IBuff buff : m_buffs)
		{
			if (buff.GetKey().equals(key))
				return Optional.of(buff);
		}
		return Optional.empty();
	}

	@Override
	public IBuff GetBuffByIndex(int index)
	{
		return m_buffs.get(index);
	}

	@Override
	public void readFromNbt(CompoundTag tag)
	{
		m_heal.Read((CompoundTag) tag.get(HP_REGEN_KEY));
	}

	@Override
	public void writeToNbt(CompoundTag tag)
	{
		CompoundTag healTag = new CompoundTag();
		m_heal.Write(healTag);
		tag.put(HP_REGEN_KEY, healTag);
	}



}
