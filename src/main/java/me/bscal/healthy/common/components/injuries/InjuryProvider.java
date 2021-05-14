package me.bscal.healthy.common.components.injuries;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import me.bscal.healthy.Healthy;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;

public class InjuryProvider implements EntityComponentInitializer
{

	public static final ComponentKey<IInjuryComponent> INJURY = ComponentRegistryV3.INSTANCE.getOrCreate(
			new Identifier(Healthy.MOD_ID, "injury"), IInjuryComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry)
	{
		registry.registerForPlayers(INJURY, InjuryComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
	}

}
