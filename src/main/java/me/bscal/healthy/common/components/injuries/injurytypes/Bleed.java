package me.bscal.healthy.common.components.injuries.injurytypes;

import me.bscal.healthy.common.components.injuries.IInjury;
import me.bscal.healthy.common.components.injuries.InjuryRegistry;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class Bleed extends EntityBleedInjury
{

	private static final float DAMAGE = 1.0f;

	public Bleed(Identifier id)
	{
		super(id);
		this.duration = 10 * 20;
	}

	@Override
	public IInjury MakeDefault()
	{
		Bleed injury = new Bleed(this.id);
		injury.duration = this.duration;
		return injury;
	}

	@Override
	public void OnStartInjury()
	{
		player.sendMessage(new TranslatableText("msg.healthy.bleed.start"), false);
	}

	@Override
	public void OnRemoveInjury()
	{
		player.sendMessage(new TranslatableText("msg.healthy.bleed.end"), false);
	}

	@Override
	public void OnTickInjury()
	{
		duration -= 20;
		if (m_counter++ % 5 == 0)
		{
			player.sendMessage(new TranslatableText("msg.healthy.bleed.tick"), false);

			if (player.getHealth() > DAMAGE)
				player.damage(InjuryRegistry.bleed(m_attacker), DAMAGE);
		}
	}
}
