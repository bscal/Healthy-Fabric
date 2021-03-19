package me.bscal.healthy.common.components.injuries;

import me.bscal.healthy.Healthy;
import me.bscal.healthy.common.components.injuries.injurytypes.Bleed;
import me.bscal.healthy.common.components.injuries.injurytypes.HeavyBleed;
import me.bscal.healthy.common.components.injuries.injurytypes.Infection;
import me.bscal.healthy.common.damage.damagesources.BleedSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class InjuryRegistry
{
	public static final int UNLIMITED_DURATION = -1;

	public static final Map<Identifier, IInjury> INJURY_TYPES = new HashMap<>();

	public static final IInjury BLEED_TYPE = new Bleed(new Identifier(Healthy.MOD_ID, "bleed"));
	public static final IInjury HEAVY_BLEED_TYPE = new HeavyBleed(new Identifier(Healthy.MOD_ID, "heavy_bleed"));
	public static final IInjury INFECTION_TYPE = new Infection(new Identifier(Healthy.MOD_ID, "infection"));
	//static final IInjury DEEP_BLEED_TYPE = new InjuryType(new Identifier(Healthy.MOD_ID, "deep_bleed"));
	//static final IInjury POISON_TYPE = new InjuryType(new Identifier(Healthy.MOD_ID, "poison"));
	//static final IInjury DISEASE_TYPE = new InjuryType(new Identifier(Healthy.MOD_ID, "disease"));

	public static DamageSource bleed(LivingEntity attacker) {
		return new BleedSource("bleed", attacker);
	}

	public static void Register()
	{
		RegisterType(BLEED_TYPE);
		RegisterType(HEAVY_BLEED_TYPE);
		RegisterType(INFECTION_TYPE);
		//RegisterType(DEEP_BLEED_TYPE);
		//RegisterType(POISON_TYPE);
		//RegisterType(DISEASE_TYPE);

		Healthy.LOGGER.info(
				"InjuryRegistry: Registered " + InjuryRegistry.INJURY_TYPES.size() + " effects.");
	}

	public static void RegisterType(IInjury type)
	{
		INJURY_TYPES.put(type.GetIdentifier(), type);
	}

	public static Optional<IInjury> GetType(Identifier id)
	{
		return Optional.of(INJURY_TYPES.get(id));
	}



}
