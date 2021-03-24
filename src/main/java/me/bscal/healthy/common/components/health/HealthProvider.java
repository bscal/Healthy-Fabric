package me.bscal.healthy.common.components.health;


import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import me.bscal.healthy.Healthy;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import net.minecraft.util.Identifier;

public final class HealthProvider implements EntityComponentInitializer
{

	public static final ComponentKey<IHealthComponent> HEALTH = ComponentRegistryV3.INSTANCE.getOrCreate(
			new Identifier(Healthy.MOD_ID, "health"), IHealthComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry)
	{
		registry.registerForPlayers(HEALTH, HealthComponent::new,
				RespawnCopyStrategy.ALWAYS_COPY);
	}
}
