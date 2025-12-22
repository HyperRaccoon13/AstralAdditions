package com.github.ethanicuss.astraladditions.mixin.yttr;

import com.unascribed.yttr.Yttr;
import com.unascribed.yttr.init.YHandledScreens;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.util.TriConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Mixin(value = Yttr.class, remap = false)
public abstract class YttrEachRegisterableFieldMixin {

	@Inject(method = "eachRegisterableField", at = @At("HEAD"), cancellable = true)
	private static <T, A extends Annotation> void skipScreenAnnotationsOnServer(Class<?> holder, Class<T> type, Class<A> anno, TriConsumer<Field, T, A> callback, CallbackInfo callbackInfo) {
		if (FabricLoader.getInstance().getEnvironmentType() != EnvType.SERVER) {
			return;
		}

		if (holder != YHandledScreens.class) {
			return;
		}

		for (Field field : holder.getDeclaredFields()) {
			if (type.isAssignableFrom(field.getType()) && Modifier.isStatic(field.getModifiers()) && !Modifier.isTransient(field.getModifiers())) {
				try {
					field.setAccessible(true);
					@SuppressWarnings("unchecked")
					T value = (T) field.get(null);

					callback.accept(field, value, null);
				} catch (Exception error) {
					throw new RuntimeException(error);
				}
			}
		}

		callbackInfo.cancel();
	}
}