package me.bscal.healthy;

import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;

public interface IBurnableCampfireBlockEntity
{

	boolean isLit();

	ActionResult addFireTicks(Item item, int ticks);

	ActionResult tryFireIgnite(Item item, int ticks, float chance);

}

