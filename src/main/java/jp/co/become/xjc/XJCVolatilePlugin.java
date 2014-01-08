package jp.co.become.xjc;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Collection;

import javax.annotation.Nonnull;

import org.xml.sax.ErrorHandler;

import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JMods;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;

public class XJCVolatilePlugin extends Plugin {

	private static final Method M_SET_FLAG = AccessController.doPrivileged(new MakeMethodAccessible(
			JMods.class, "setFlag", new Class<?>[] { int.class, boolean.class }));
	private static final String OPTION_NAME = "Xvolatile";

	private static final Object[] TO_VOLATILE_ARGS = { Integer.valueOf(JMod.VOLATILE), Boolean.TRUE };

	private static void setVolatile(@Nonnull final JMods mods) {
		try {
			M_SET_FLAG.invoke(mods, TO_VOLATILE_ARGS);
		} catch (final ReflectiveOperationException e) {
			throw new IllegalStateException(e);
		}
	}

	private static void visit(final Collection<? extends ClassOutline> classOutlines) {
		for (final ClassOutline classOutline : classOutlines) {
			final JDefinedClass definedClass = classOutline.implClass;
			for (final JFieldVar field : definedClass.fields().values()) {
				final JMods mods = field.mods();
				if (!ReflectionUtil.isStatic(mods) && ReflectionUtil.isList(field.type())) {
					setVolatile(mods);
				}
			}
		}
	}

	@Override
	public String getOptionName() {
		return OPTION_NAME;
	}

	@Override
	public String getUsage() {
		return "  -" + OPTION_NAME + "\t: List fields are made volatile";
	}

	@Override
	public boolean run(final Outline outline, final Options options, final ErrorHandler errorHandler) {
		visit(outline.getClasses());
		return true;
	}
}
