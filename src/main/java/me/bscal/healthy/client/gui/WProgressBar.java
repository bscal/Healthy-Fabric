package me.bscal.healthy.client.gui;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Texture;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

/**
 * Was a alternative to WBar to easily show float values
 */
public class WProgressBar extends WWidget
{
	protected final Texture bg;
	protected final Texture bar;
	public final Direction direction;
	public float value;
	public float maxValue;

	public WProgressBar(Texture bg, Texture bar, Direction direction, float maxValue)
	{
		this.bg = bg;
		this.bar = bar;
		this.direction = direction;
		this.maxValue = maxValue;
	}

	@Override
	public boolean canResize()
	{
		return false;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY)
	{
		if (bg != null)
		{
			ScreenDrawing.texturedRect(matrices, x, y, getWidth(), getHeight(), bg, 0xFFFFFFFF);
		}
		else
		{
			ScreenDrawing.coloredRect(matrices, x, y, getWidth(), getHeight(),
					ScreenDrawing.colorAtOpacity(0x000000, 0.25f));
		}

		float percent = value / maxValue;
		if (percent < 0)
			percent = 0f;
		if (percent > 1)
			percent = 1f;

		int barMax = getWidth();
		if (direction == Direction.DOWN || direction == Direction.UP)
			barMax = getHeight();
		percent = ((int) (percent * barMax)) / (float) barMax; //Quantize to bar size

		int barSize = (int) (barMax * percent);
		if (barSize <= 0)
			return;

		switch (direction)
		{
		case UP:
		{
			int left = x;
			int top = y + getHeight();
			top -= barSize;
			if (bar != null)
			{
				ScreenDrawing.texturedRect(matrices, left, top, getWidth(), barSize, bar.image, bar.u1,
						MathHelper.lerp(percent, bar.v2, bar.v1), bar.u2, bar.v2, 0xFFFFFFFF);
			}
			else
			{
				ScreenDrawing.coloredRect(matrices, left, top, getWidth(), barSize,
						ScreenDrawing.colorAtOpacity(0xFFFFFF, 0.5f));
			}
			break;
		}
		case RIGHT:
		{
			if (bar != null)
			{
				ScreenDrawing.texturedRect(matrices, x, y, barSize, getHeight(), bar.image, bar.u1, bar.v1,
						MathHelper.lerp(percent, bar.u1, bar.u2), bar.v2, 0xFFFFFFFF);
			}
			else
			{
				ScreenDrawing.coloredRect(matrices, x, y, barSize, getHeight(),
						ScreenDrawing.colorAtOpacity(0xFFFFFF, 0.5f));
			}
			break;
		}
		case DOWN:
		{
			if (bar != null)
			{
				ScreenDrawing.texturedRect(matrices, x, y, getWidth(), barSize, bar.image, bar.u1, bar.v1,
						bar.u2, MathHelper.lerp(percent, bar.v1, bar.v2), 0xFFFFFFFF);
			}
			else
			{
				ScreenDrawing.coloredRect(matrices, x, y, getWidth(), barSize,
						ScreenDrawing.colorAtOpacity(0xFFFFFF, 0.5f));
			}
			break;
		}
		case LEFT:
		{
			int left = x + getWidth();
			int top = y;
			left -= barSize;
			if (bar != null)
			{
				ScreenDrawing.texturedRect(matrices, left, top, barSize, getHeight(), bar.image,
						MathHelper.lerp(percent, bar.u2, bar.u1), bar.v1, bar.u2, bar.v2,
						0xFFFFFFFF);
			}
			else
			{
				ScreenDrawing.coloredRect(matrices, left, top, barSize, getHeight(),
						ScreenDrawing.colorAtOpacity(0xFFFFFF, 0.5f));
			}
			break;
		}
		}
	}

	public enum Direction
	{
		UP, RIGHT, DOWN, LEFT;
	}
}