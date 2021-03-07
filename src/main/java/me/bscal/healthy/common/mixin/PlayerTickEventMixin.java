package me.bscal.healthy.common.mixin;

import me.bscal.healthy.common.events.callbacks.PlayerTickCallback;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerTickEventMixin
{

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;updateWaterSubmersionState()Z"), method = "tick", cancellable = false)
	public void tick(CallbackInfo cb)
	{
		PlayerEntity player = (PlayerEntity) (Object) this;
		PlayerTickCallback.EVENT.invoker().tick(player);
	}
}
