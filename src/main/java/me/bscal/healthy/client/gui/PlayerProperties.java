package me.bscal.healthy.client.gui;

import net.minecraft.screen.PropertyDelegate;

public class PlayerProperties implements PropertyDelegate
{

	public static final int HP = 0;
	public static final int MAX_HP = 1;

	private final int[] m_properties = new int[] {
			20,
			20
	};

	@Override
	public int get(int index)
	{
		return m_properties[index];
	}

	@Override
	public void set(int index, int value)
	{
		m_properties[index] = value;
	}

	@Override
	public int size()
	{
		return m_properties.length;
	}
}
