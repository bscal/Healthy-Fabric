package me.bscal.healthy.client.gui;

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.data.Texture;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * A WSprite object that adds support for tooltips. Use {@code setTooltipData} to
 * set the tooltips data. You do not need to call {@code addTooltip}
 */
public class WSpriteTooltip extends WSprite
{

	private Text[] m_toolTipText;

	public WSpriteTooltip(Texture texture)
	{
		super(texture);
	}

	public WSpriteTooltip(Identifier image)
	{
		super(image);
	}

	public WSpriteTooltip(Identifier image, float u1, float v1, float u2, float v2)
	{
		super(image, u1, v1, u2, v2);
	}

	public WSpriteTooltip(int frameTime, Identifier... frames)
	{
		super(frameTime, frames);
	}

	public WSpriteTooltip(int frameTime, Texture... frames)
	{
		super(frameTime, frames);
	}

	@Override
	public void addTooltip(TooltipBuilder tooltip)
	{
		if (m_toolTipText != null)
			tooltip.add(m_toolTipText);
	}

	public void setTooltipData(Text... tooltipText)
	{
		m_toolTipText = tooltipText;
	}

}
