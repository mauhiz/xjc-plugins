package jp.co.become.xjc;

import java.lang.reflect.Field;
import java.security.PrivilegedAction;

import javax.annotation.Nonnull;

public class MakeFieldAccessible implements PrivilegedAction<Field> {
	private final Class<?> clazz;
	private final String fieldName;

	public MakeFieldAccessible(@Nonnull final Class<?> clazz, @Nonnull final String fieldName) {
		this.clazz = clazz;
		this.fieldName = fieldName;
	}

	@Override
	@Nonnull
	public Field run() {
		try {
			final Field ret = clazz.getDeclaredField(fieldName);
			ret.setAccessible(true);
			return ret;
		} catch (final ReflectiveOperationException e) {
			throw new IllegalStateException(e);
		}
	}
}