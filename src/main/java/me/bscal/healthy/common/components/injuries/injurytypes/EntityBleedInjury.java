package me.bscal.healthy.common.components.injuries.injurytypes;

import me.bscal.healthy.Healthy;
import me.bscal.healthy.common.components.injuries.InjuryRegistry;
import me.bscal.healthy.common.components.injuries.InjuryType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class EntityBleedInjury extends InjuryType
{

	protected LivingEntity m_attacker;
	protected UUID m_uuid;
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
	public void Write(NbtCompound tag)
	{
		super.Write(tag);
		tag.putInt("count", m_counter);
		if (m_attacker != null)
			tag.putInt("attackerID", m_attacker.getId());
	}

	@Override
	public void Read(NbtCompound tag)
	{
		super.Read(tag);
		m_counter = tag.getInt("count");
		//if (tag.containsUuid("UUID")) {
		//	m_uuid = tag.getUuid("UUID");
		//	m_attacker = MinecraftClient.getInstance().
		//}

		Entity ent = (player != null) ? player.getEntityWorld().getEntityById(tag.getInt("attackerID")) : null;
		if (ent instanceof LivingEntity)
			m_attacker = (LivingEntity) ent;
	}

}
