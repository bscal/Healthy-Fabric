package me.bscal.healthy.common.components.injuries.injurytypes;

import me.bscal.healthy.common.components.injuries.InjuryRegistry;
import me.bscal.healthy.common.components.injuries.InjuryType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

public abstract class EntityBleedInjury extends InjuryType
{

	protected LivingEntity m_attacker;
	protected int m_counter;

	public EntityBleedInjury(Identifier id)
	{
		super(id);
	}

	public void SetAttacker(LivingEntity attacker)
	{
		m_attacker = attacker;
	}

	@Override
	public DamageSource GetDamageSource()
	{
		return InjuryRegistry.bleed(m_attacker);
	}

	@Override
	public void Write(CompoundTag tag)
	{
		super.Write(tag);
		tag.putInt("count", m_counter);
		if (m_attacker != null)
			tag.putInt("attacker", m_attacker.getEntityId());
	}

	@Override
	public void Read(CompoundTag tag)
	{
		super.Read(tag);
		m_counter = tag.getInt("count");
		Entity ent = (player != null) ? player.getEntityWorld().getEntityById(tag.getInt("attacker")) : null;
		if (ent instanceof LivingEntity)
			m_attacker = (LivingEntity) ent;
	}

}
