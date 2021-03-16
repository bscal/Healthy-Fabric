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

	public InjuryType()
	{
	}

	public InjuryType(PlayerEntity player)
	{
		this.player = player;
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
		tag.putUuid("uuid", player.getUuid());
		tag.putInt("dur", duration);
	}

	@Override
	public void Read(CompoundTag tag)
	{
		id = new Identifier(tag.getString("id"));
		player = Healthy.GetServer().getPlayerManager().getPlayer(tag.getUuid("uuid"));
		duration = tag.getInt("dur");
	}

}
