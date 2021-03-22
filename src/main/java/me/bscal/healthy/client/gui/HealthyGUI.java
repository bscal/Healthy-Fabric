package me.bscal.healthy.client.gui;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.client.NinePatch;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.*;
import me.bscal.healthy.Healthy;
import me.bscal.healthy.client.HealthyClient;
import me.bscal.healthy.common.components.health.HealthProvider;
import me.bscal.healthy.common.components.injuries.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class HealthyGUI extends LightweightGuiDescription
{

	private static final Texture BAR = new Texture(new Identifier(Healthy.MOD_ID, "textures/gui/prog_bar.png"));
	private static final Texture BG = new Texture(new Identifier(Healthy.MOD_ID, "textures/gui/prog_bg.png"));

	private static final BackgroundPainter HEALTHY_BG = BackgroundPainter.createNinePatch(
			new Identifier(Healthy.MOD_ID, "textures/gui/prog_border_4.png"), 4);

	public HealthyGUI()
	{
		setPropertyDelegate(HealthyClient.PROPERTIES);

		WGridPanel root = new WGridPanel();
		setRootPanel(root);
		root.setSize(17 * 18, 11 * 18);

		ClientPlayerEntity player = MinecraftClient.getInstance().player;
		if (player != null)
		{
			WPlainPanel panel = new WPlainPanel();
			panel.setBackgroundPainter(HEALTHY_BG);
			root.add(panel, 0, 1, 8, 1);

			WBar bar = new WBar(BG, BAR, PlayerProperties.HP, PlayerProperties.MAX_HP, WBar.Direction.RIGHT);
			root.add(bar, 0, 1, 8, 1);

			WDynamicLabel hpLabel = new WDynamicLabel(() -> I18n.translate("text.healthy.gui.hp", player.getHealth(), player.getMaxHealth()));
			root.add(hpLabel, 0, 1);
		}

		WLabel buffLabel = new WLabel("Buffs and Debuffs");
		root.add(buffLabel, 0, 3);

		WGridPanel grid = new WGridPanel(9);
		grid.setBackgroundPainter(HEALTHY_BG);
		IInjuryComponent injuries = InjuryProvider.INJURY.get(MinecraftClient.getInstance().player);
		int x = 0;
		int y = 0;
		for (IInjury injury : injuries.GetInjuries().values())
		//for (int i = 0; i < 30; i++)
		{
			WSpriteTooltip sprite = new WSpriteTooltip(BAR);
			sprite.setTooltipData(injury.GetDescription());

			float r = Healthy.RAND.nextFloat();

			if (r < .33)
				sprite.setTint(Color.BLUE.toRgb());
			else if (r < .66)
				sprite.setTint(Color.RED.toRgb());
			else
				sprite.setTint(Color.GREEN.toRgb());

			grid.add(sprite, x, y, 2, 2);
			x += 3;
			if (x % 27 == 0)
			{
				y += 3;
				x = 0;
			}
		}
		root.add(grid, 2, 5);

		root.validate(this);
	}
}
