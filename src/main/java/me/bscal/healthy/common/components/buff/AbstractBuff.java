package me.bscal.healthy.common.components.buff;

import net.minecraft.nbt.NbtCompound;

public abstract class AbstractBuff implements IBuff
{

	public String name;
	public int id;

	public AbstractBuff() {}

	public AbstractBuff(String name)
	{
		this.name = name;
	}

	public AbstractBuff(String name, int id)
	{
		this.name = name;
		this.id = id;
	}

	@Override
	public String GetName()
	{
		return name;
	}

	@Override
	public int GetID()
	{
		return id;
	}

	@Override
	public String GetKey()
	{
		return name + ":" + id;
	}

	@Override
	public void Write(NbtCompound tag)
	{
		tag.putString("name", name);
		tag.putInt("id", id);
	}

	@Override
	public void Read(NbtCompound tag)
	{
		name = tag.getString("name");
		id = tag.getInt("id");
	}

}
