package me.bscal.healthy.common.components.injuries;

import me.bscal.healthy.Healthy;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class InjuryComponent implements IInjuryComponent
{

	private final Map<Identifier, IInjury> m_injuries = new HashMap<>();

	@Override
	public void readFromNbt(CompoundTag tag)
	{
		if (tag.contains("injuries"))
		{
			m_injuries.clear();
			ListTag list = tag.getList("injuries", 10);
			list.forEach((iTag) -> {
				CompoundTag cTag =(CompoundTag)iTag;
				InjuryRegistry.GetType(new Identifier(cTag.getString("id"))).ifPresent((regInjury) -> {
					IInjury injury = regInjury.CreateNew();
					injury.Read(cTag);
					m_injuries.put(injury.GetIdentifier(), injury);
					Healthy.LOGGER.info("reading");
				});
			});
		}
	}

	@Override
	public void writeToNbt(CompoundTag tag)
	{
		if (m_injuries.size() > 0)
		{
			ListTag list = new ListTag();
			for (IInjury injury : m_injuries.values())
			{
				Healthy.LOGGER.info("writing");
				CompoundTag writer = new CompoundTag();
				injury.Write(writer);
				list.add(writer);
			}
			tag.put("injuries", list);
		}
	}

	@Override
	public Map<Identifier, IInjury> GetInjuries()
	{
		return m_injuries;
	}

	@Override
	public void UpdateInjuries()
	{
		Iterator<Map.Entry<Identifier, IInjury>> iter = m_injuries.entrySet().iterator();
		while (iter.hasNext())
		{
			Map.Entry<Identifier, IInjury> next = iter.next();
			if (m_injuries.containsKey(next.getKey()))
			{
				next.getValue().OnTickInjury();
				if (next.getValue().GetDuration() <= 0)
				{
					next.getValue().OnRemoveInjury();
					iter.remove();
				}
			}
		}
	}

	@Override
	public IInjury GetInjury(IInjury injury)
	{
		return m_injuries.get(injury.GetIdentifier());
	}

	@Override
	public void AddInjury(IInjury injury, boolean trigger)
	{
		IInjury lastMapping = m_injuries.put(injury.GetIdentifier(), injury);
		if (trigger && lastMapping == null)
			injury.OnStartInjury();
	}

	@Override
	public void RemoveInjury(IInjury injury, boolean trigger)
	{
		IInjury lastMapping = m_injuries.remove(injury.GetIdentifier());
		if (trigger && lastMapping != null)
		{
			injury.OnRemoveInjury();
			lastMapping = null;
		}
	}

	@Override
	public boolean HasInjury(IInjury injury)
	{
		return m_injuries.containsKey(injury.GetIdentifier());
	}
}
