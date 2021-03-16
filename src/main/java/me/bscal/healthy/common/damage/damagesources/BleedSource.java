package me.bscal.healthy.common.damage.damagesources;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.EntityDamageSource;
import org.jetbrains.annotations.Nullable;

public class BleedSource extends EntityDamageSource
{
	public BleedSource(String name, @Nullable Entity source)
	{
		super(name, source);
		setBypassesArmor();
		setUnblockable();
	}
}
