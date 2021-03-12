package me.bscal.healthy.common.items;

import me.bscal.healthy.Healthy;
import me.bscal.healthy.common.components.health.Heal;
import me.bscal.healthy.common.components.health.HealthComponent;
import me.bscal.healthy.common.components.health.HealthProvider;
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
				serverUser.damage(DamageSource.MAGIC, 10f);
			}

			HealthProvider.HEALTH.get(serverUser)
					.AddHealing(Heal.Builder(10, 10 * 20, 20));

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
