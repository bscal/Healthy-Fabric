package me.bscal.healthy.common.components.buff;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;

public interface IBuff
{

	int GetDuration();

	int GetRemainingDuration();

	int GetTicksPerUpdate();

	boolean IsGood();

	boolean IsFinished();

	boolean CanUpdate(LivingEntity entity);

	void Update(LivingEntity entity);

	void UpdateDuration(LivingEntity entity);

	void Read(CompoundTag tag);

	void Write(CompoundTag tag);

}
