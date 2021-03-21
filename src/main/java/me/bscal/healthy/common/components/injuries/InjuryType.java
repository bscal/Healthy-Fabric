package me.bscal.healthy.common.components.injuries;

import me.bscal.healthy.Healthy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

public abstract class InjuryType implements IInjury
{

	protected Identifier id;
	protected PlayerEntity player;
	protected int duration;

	public InjuryType(Identifier id)
	{
		this.id = id;
	}

	public InjuryType(PlayerEntity player)
	{
		this.player = player;
	}

	public void SetPlayer(PlayerEntity player)
	{
		this.player = player;
	}

	@Override
	public IInjury MakeNew(PlayerEntity player)
	{
		InjuryType injury = (InjuryType) MakeDefault();
		injury.player = player;
		return injury;
	}

	@Override
	public Identifier GetIdentifier()
	{
		return id;
	}

	@Override
	public PlayerEntity GetPlayer()
	{
		return player;
	}

	@Override
	public int GetDuration()
	{
		return duration;
	}

	@Override
	public void SetDuration(int dur)
	{
		duration = dur;
	}

	@Override
	public void Write(CompoundTag tag)
	{
		tag.putString("id", id.toString());
		tag.putInt("dur", duration);
		tag.putUuid("uuid", player.getUuid());
	}

	@Override
	public void Read(CompoundTag tag)
	{
		id = new Identifier(tag.getString("id"));
		duration = tag.getInt("dur");
		if (tag.contains("uuid") && player == null)
			player = Healthy.GetServer().getPlayerManager().getPlayer("uuid");
	}

}
