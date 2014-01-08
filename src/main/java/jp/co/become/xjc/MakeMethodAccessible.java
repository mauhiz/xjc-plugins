package jp.co.become.xjc;

import java.lang.reflect.Method;
import java.security.PrivilegedAction;

import javax.annotation.Nonnull;

public class MakeMethodAccessible implements PrivilegedAction<Method> {

	private final Class<?> clazz;
	private final String methodName;
	private final Class<?>[] parameterTypes;

	public MakeMethodAccessible(@Nonnull final Class<?> clazz, @Nonnull final String methodName,
			@Nonnull final Class<?>[] parameterTypes) {
		this.clazz = clazz;
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
	}

	@Override
	@Nonnull
	public Method run() {
		try {
			final Method ret = clazz.getDeclaredMethod(methodName, parameterTypes);
			ret.setAccessible(true);
			return ret;
		} catch (final ReflectiveOperationException e) {
			throw new IllegalStateException(e);
		}
	}
}