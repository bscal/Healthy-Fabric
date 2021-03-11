package me.bscal.healthy.client.gui;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.client.NinePatch;
import io.github.cottonmc.cotton.gui.widget.WBar;
import io.github.cottonmc.cotton.gui.widget.WBox;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.Texture;
import me.bscal.healthy.Healthy;
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


		root.validate(this);
	}
}
