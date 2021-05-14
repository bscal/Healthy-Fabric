package me.bscal.healthy.common.components.buff;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;

public interface IBuff
{

	String GetName();

	int GetID();

	String GetKey();

	int GetDuration();

	int GetRemainingDuration();

	int GetTicksPerUpdate();

	boolean IsGood();

	boolean IsFinished();

	boolean CanUpdate(LivingEntity entity);

	void Update(LivingEntity entity);

	void UpdateDuration(LivingEntity entity);

	void Read(NbtCompound tag);

	void Write(NbtCompound tag);

}
