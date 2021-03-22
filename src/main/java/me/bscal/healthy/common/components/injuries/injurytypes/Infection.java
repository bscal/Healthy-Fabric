package me.bscal.healthy.common.components.injuries.injurytypes;

import me.bscal.healthy.common.components.injuries.IInjury;
import me.bscal.healthy.common.components.injuries.InjuryRegistry;
import me.bscal.healthy.common.components.injuries.InjuryType;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class Infection extends InjuryType
{
	private static final String TEXT_STR = "tooltip.healthy.infection";

	protected static final EntityAttributeModifier MOD = new EntityAttributeModifier("infection", -4,
			EntityAttributeModifier.Operation.ADDITION);

	private int m_counter = 0;

	public Infection(Identifier id)
	{
		super(id);
		this.duration = 10 * 20;
	}

	@Override
	public IInjury MakeDefault()
	{
		return new Infection(this.id);
	}

	@Override
	public void OnStartInjury()
	{
		player.sendMessage(new TranslatableText("msg.healthy.infection.start").formatted(Formatting.GREEN), false);
	}

	@Override
	public void OnRemoveInjury()
	{
		EntityAttributeInstance instance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
		if (instance != null)
			instance.tryRemoveModifier(MOD.getId());
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
		instance.addTemporaryModifier(MOD);
	}

	@Override
	public Text[] GetDescription()
	{
		return new Text[] {
				new TranslatableText(TEXT_STR + "_0").formatted(Formatting.BLUE, Formatting.BOLD),
				new TranslatableText(TEXT_STR + "_1a").formatted(Formatting.GRAY).append(new TranslatableText(TEXT_STR + "_1b").formatted(Formatting.RED)),
				new TranslatableText(TEXT_STR + "_2", duration / 20).formatted(Formatting.GRAY),
				new TranslatableText(TEXT_STR + "_3"),
				new TranslatableText(TEXT_STR + "_4").formatted(Formatting.GRAY),
				new TranslatableText(TEXT_STR + "_5", InjuryRegistry.INFECTION_TYPE.GetDuration() / 20).formatted(Formatting.GRAY),
				new TranslatableText(TEXT_STR + "_6").formatted(Formatting.GRAY)
		};
	}

	@Override
	public DamageSource GetDamageSource()
	{
		return DamageSource.GENERIC;
	}

}
