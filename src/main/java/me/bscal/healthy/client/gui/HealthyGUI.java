package me.bscal.healthy.client.gui;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.client.NinePatch;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.Texture;
import me.bscal.healthy.Healthy;
import me.bscal.healthy.common.components.health.HealthProvider;
import me.bscal.healthy.common.components.injuries.IInjury;
import me.bscal.healthy.common.components.injuries.IInjuryComponent;
import me.bscal.healthy.common.components.injuries.InjuryComponent;
import me.bscal.healthy.common.components.injuries.InjuryProvider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class HealthyGUI extends LightweightGuiDescription
{

	private static final Texture BAR = new Texture(new Identifier(Healthy.MOD_ID, "textures/gui/prog_bar.png"));
	private static final Texture BG = new Texture(new Identifier(Healthy.MOD_ID, "textures/gui/prog_bg.png"));


	public HealthyGUI()
	{
		WGridPanel root = new WGridPanel();
		setRootPanel(root);
		root.setSize(300, 200);

		WProgressBar hpBar = new WProgressBar(BG, BAR, WProgressBar.Direction.RIGHT, 100);
		hpBar.value = 50f;

		WBox box = new WBox(Axis.HORIZONTAL);
		box.setSize(80, 24);
		box.setBackgroundPainter(BackgroundPainter.createNinePatch(new Identifier(Healthy.MOD_ID, "textures/gui/prog_border.png"), 2));
		box.add(hpBar, 80, 24);
		root.add(box,1, 1);

		WLabel label = new WLabel("HP");
		root.add(label, 1, 1);

		WGridPanel grid = new WGridPanel();
		IInjuryComponent injuries = InjuryProvider.INJURY.get(MinecraftClient.getInstance().player);
		int x = 0;
		int y = 1;
		for (IInjury injury : injuries.GetInjuries().values())
		{
			WLabel sprite = new WLabel(injury.GetIdentifier().getPath());
			grid.add(sprite, x++, y);
		}
		root.add(grid, 12, 12);

		root.validate(this);
	}
}
