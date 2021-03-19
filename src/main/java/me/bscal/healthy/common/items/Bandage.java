package me.bscal.healthy.common.items;

import me.bscal.healthy.Healthy;
import me.bscal.healthy.common.components.health.Heal;
import me.bscal.healthy.common.components.health.HealthComponent;
import me.bscal.healthy.common.components.health.HealthProvider;
import me.bscal.healthy.common.components.injuries.IInjuryComponent;
import me.bscal.healthy.common.components.injuries.InjuryComponent;
import me.bscal.healthy.common.components.injuries.InjuryProvider;
import me.bscal.healthy.common.components.injuries.InjuryRegistry;
import me.bscal.healthy.common.components.injuries.injurytypes.HeavyBleed;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class Bandage extends Item
{

	public Bandage(Settings settings)
	{
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
	{
		ItemStack itemstack = user.getStackInHand(hand);
		if (!world.isClient)
		{
			user.setCurrentHand(hand);
			return TypedActionResult.success(itemstack);
		}
		return TypedActionResult.fail(itemstack);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user)
	{
		if (!world.isClient && user instanceof ServerPlayerEntity)
		{
			ServerPlayerEntity serverUser = (ServerPlayerEntity) user;

			if (Healthy.DEBUG)
			{
				//serverUser.damage(DamageSource.MAGIC, 10f);
			}

			Heal heal = new Heal("healing", 0).SetHealing(10, 10 * 20, 20);
			HealthProvider.HEALTH.get(serverUser).AddHealing(heal);

			IInjuryComponent injuries = InjuryProvider.INJURY.get(serverUser);

			if (injuries.HasInjury(InjuryRegistry.BLEED_TYPE))
			{
				injuries.RemoveInjury(InjuryRegistry.BLEED_TYPE, true);
			}
			else if (injuries.HasInjury(InjuryRegistry.HEAVY_BLEED_TYPE))
			{
				HeavyBleed heavyBleed = (HeavyBleed)injuries.GetInjury(InjuryRegistry.HEAVY_BLEED_TYPE);
				heavyBleed.SetBandageCount(60 * 20);
			}

			stack.decrement(1);
		}
		return stack;
	}

	@Override
	public int getMaxUseTime(ItemStack stack)
	{
		return 48;
	}

	@Override
	public UseAction getUseAction(ItemStack stack)
	{
		return UseAction.BOW;
	}
}
