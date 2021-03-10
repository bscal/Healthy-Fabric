package me.bscal.healthy.client.gui;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.minecraft.text.Text;

public class HealthyScreen extends CottonClientScreen
{
	public HealthyScreen(GuiDescription description)
	{
		super(Text.of("Health Info"), description);
	}
}
