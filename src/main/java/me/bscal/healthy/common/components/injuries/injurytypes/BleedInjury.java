package me.bscal.healthy.common.components.injuries.injurytypes;

import me.bscal.healthy.common.components.injuries.IInjury;
import me.bscal.healthy.common.components.injuries.InjuryProvider;
import me.bscal.healthy.common.components.injuries.InjuryRegistry;
import me.bscal.healthy.common.components.injuries.InjuryType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class BleedInjury extends InjuryType
{

	private int m_counter = 0;

	public BleedInjury(Identifier id)
	{
		this.id = id;
		this.duration = 10 * 20;
	}

	public BleedInjury(PlayerEntity player)
	{
		this(InjuryRegistry.BLEED_TYPE.GetIdentifier());
		this.player = player;
	}

	@Override
	public IInjury CreateNew()
	{
		return new BleedInjury(InjuryRegistry.BLEED_TYPE.GetIdentifier());
	}

	@Override
	public void CreateNewAndApply(PlayerEntity player)
	{
		InjuryProvider.INJURY.get(player).AddInjury(new BleedInjury(player), true);
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
		m_counter++;
		if (m_counter % 5 == 0)
		{
			player.sendMessage(new TranslatableText("msg.healthy.bleed.tick"), false);
		}
	}

	@Override
	public DamageSource GetDamageSource()
	{
		return null;
	}

	@Override
	public void Write(CompoundTag tag)
	{
		super.Write(tag);
		tag.putInt("count", m_counter);
	}

	@Override
	public void Read(CompoundTag tag)
	{
		super.Read(tag);
		m_counter = tag.getInt("count");
	}
}
