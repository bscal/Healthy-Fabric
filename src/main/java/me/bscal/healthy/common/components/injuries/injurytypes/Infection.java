package me.bscal.healthy.common.components.injuries.injurytypes;

import me.bscal.healthy.common.components.injuries.IInjury;
import me.bscal.healthy.common.components.injuries.InjuryType;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class Infection extends InjuryType
{

	protected static final EntityAttributeModifier MOD = new EntityAttributeModifier("infection", -4,
			EntityAttributeModifier.Operation.ADDITION);

	private int m_counter = 0;

	public Infection(Identifier id)
	{
		super(id);
		this.duration = 30 * 20;
	}

	@Override
	public IInjury MakeDefault()
	{
		return new Infection(this.id);
	}

	@Override
	public void OnStartInjury()
	{
	}

	@Override
	public void OnRemoveInjury()
	{
		EntityAttributeInstance instance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
		if (instance == null || !instance.hasModifier(MOD))
			return;
		instance.removeModifier(MOD);
	}

	@Override
	public void OnTickInjury()
	{
		if (!player.isAlive()) return;

		duration -= 20;
		m_counter++;
		if (duration != 0 && m_counter % 10 == 0)
			player.sendMessage(new TranslatableText("msg.healthy.infection.tick").formatted(Formatting.GREEN), false);

		EntityAttributeInstance instance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
		if (instance == null || instance.hasModifier(MOD))
			return;
		instance.addPersistentModifier(MOD);
	}

	@Override
	public DamageSource GetDamageSource()
	{
		return DamageSource.GENERIC;
	}
}
