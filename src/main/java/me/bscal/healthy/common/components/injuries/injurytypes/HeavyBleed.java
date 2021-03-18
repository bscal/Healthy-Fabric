package me.bscal.healthy.common.components.injuries.injurytypes;

import me.bscal.healthy.common.components.injuries.IInjury;
import me.bscal.healthy.common.components.injuries.InjuryRegistry;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class HeavyBleed extends EntityBleedInjury
{

	private static final float DAMAGE = 1.0f;

	public HeavyBleed(Identifier id)
	{
		super(id);
		this.duration = 10 * 20;
	}

	@Override
	public IInjury MakeDefault()
	{
		HeavyBleed injury = new HeavyBleed(this.id);
		injury.duration = this.duration;
		return injury;
	}

	@Override
	public void OnStartInjury()
	{
	}

	@Override
	public void OnRemoveInjury()
	{
	}

	@Override
	public void OnTickInjury()
	{
		duration -= 20;
		if (m_counter++ % 2 == 0)
		{
			player.sendMessage(new TranslatableText("msg.healthy.bleed.tick"), false);

			player.damage(InjuryRegistry.bleed(m_attacker), DAMAGE);
		}
	}
}
