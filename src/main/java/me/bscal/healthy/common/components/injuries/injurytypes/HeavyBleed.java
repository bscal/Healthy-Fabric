package me.bscal.healthy.common.components.injuries.injurytypes;

import me.bscal.healthy.common.components.injuries.IInjury;
import me.bscal.healthy.common.components.injuries.InjuryRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class HeavyBleed extends EntityBleedInjury
{

	private static final float DAMAGE = 1.0f;
	private static final String TEXT_STR = "tooltip.healthy.heavy_bleed";

	protected int m_bandageCounter = 0;

	public HeavyBleed(Identifier id)
	{
		super(id);
		this.duration = 10 * 20;
	}

	public void SetBandageCount(int counter)
	{
		player.sendMessage(new TranslatableText("msg.healthy.heavybleed.bandage"), false);
		m_bandageCounter = counter;
	}

	public boolean CanProcessBandage()
	{
		return m_bandageCounter <= 0;
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
		if (!player.isAlive()) return;

		duration -= 20;
		m_bandageCounter -= 20;
		if (m_counter++ % 2 == 0 && CanProcessBandage())
		{
			player.sendMessage(new TranslatableText("msg.healthy.heavybleed.tick").formatted(Formatting.RED), false);

			player.damage(InjuryRegistry.bleed(m_attacker), DAMAGE);
		}
	}

	@Override
	public Text[] GetDescription()
	{
		return new Text[] {
				new TranslatableText(TEXT_STR + "_0").formatted(Formatting.BLUE, Formatting.BOLD),
				new TranslatableText(TEXT_STR + "_1a").formatted(Formatting.GRAY).append(new TranslatableText(TEXT_STR + "_1b").formatted(Formatting.RED)),
				new TranslatableText(TEXT_STR + "_2", duration / 20).formatted(Formatting.GRAY),
				new TranslatableText(TEXT_STR + "_3"),
				new TranslatableText(TEXT_STR + "_4", 1, 2).formatted(Formatting.GRAY),
				new TranslatableText(TEXT_STR + "_5", InjuryRegistry.HEAVY_BLEED_TYPE.GetDuration() / 20).formatted(Formatting.GRAY),
				new TranslatableText(TEXT_STR + "_6").formatted(Formatting.GRAY)
		};
	}

	@Override
	public void Read(NbtCompound tag)
	{
		super.Read(tag);
		tag.putInt("bandageCounter", m_bandageCounter);
	}

	@Override
	public void Write(NbtCompound tag)
	{
		super.Write(tag);
		m_bandageCounter = tag.getInt("bandageCounter");
	}
}
