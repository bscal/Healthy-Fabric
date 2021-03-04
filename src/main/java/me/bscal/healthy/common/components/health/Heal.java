package me.bscal.healthy.common.components.health;

public class Heal
{

	public static final Heal ZERO = new Heal();

	public boolean instant;
	public float totalHealing;
	public int durationInTicks;
	public int ticksPerUpdate;
	public float healingPerUpdate;

	public float healingDone;
	public boolean finished;

	public float Consume()
	{
		durationInTicks -= ticksPerUpdate;

		if (durationInTicks <= 0)
			finished = true;

		return healingPerUpdate;
	}

	public static Heal Builder(float totalHealing, int durInTicks, int ticksPerHeal)
	{
		Heal heal = new Heal();
		heal.totalHealing = totalHealing;
		heal.durationInTicks = durInTicks;
		heal.ticksPerUpdate = ticksPerHeal;
		heal.healingPerUpdate = totalHealing / (float)(durInTicks / ticksPerHeal);
		return heal;
	}

	public Heal Instant(boolean instant)
	{
		this.instant = instant;
		this.healingPerUpdate = this.totalHealing;
		this.durationInTicks = this.ticksPerUpdate;
		return this;
	}


}
