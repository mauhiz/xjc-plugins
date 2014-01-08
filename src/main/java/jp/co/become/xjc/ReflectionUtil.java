package jp.co.become.xjc;

import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import com.sun.codemodel.JMod;
import com.sun.codemodel.JMods;
import com.sun.codemodel.JType;

public enum ReflectionUtil {
	;

	private static final Pattern LIST_PATTERN = Pattern.compile("java\\.util\\.(:?Array|Linked)?List(:?<.+>)?");

	public static boolean isList(@Nonnull final JType type) {
		return LIST_PATTERN.matcher(type.binaryName()).matches();
	}

	public static boolean isStatic(@Nonnull final JMods mods) {
		return (mods.getValue() & JMod.STATIC) != 0;
	}
}
