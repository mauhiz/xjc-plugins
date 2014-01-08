package jp.co.become.xjc;

import java.util.Collection;

import javax.annotation.Nonnull;

import org.xml.sax.ErrorHandler;

import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMethod;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;
import com.sun.tools.xjc.outline.PackageOutline;

public class NonNullPlugin extends Plugin {

	private static final String OPTION_NAME = "Xnonnull";

	private static void annotateClasses(final Collection<? extends ClassOutline> classOutlines) {
		for (final ClassOutline classOutline : classOutlines) {
			final JDefinedClass definedClass = classOutline.implClass;
			for (final JMethod method : definedClass.methods()) {
				if (ReflectionUtil.isList(method.type())) {
					method.annotate(Nonnull.class);
				}
			}
		}
	}

	private static void annotateFactory(final Iterable<? extends PackageOutline> packageContexts) {
		for (final PackageOutline packageOutline : packageContexts) {
			for (final JMethod method : packageOutline.objectFactory().methods()) {
				method.annotate(Nonnull.class);
			}
		}
	}

	@Override
	public String getOptionName() {
		return OPTION_NAME;
	}

	@Override
	public String getUsage() {
		return "  -" + OPTION_NAME + "\t: List getters and Object factory are @Nonnull";
	}

	@Override
	public boolean run(final Outline outline, final Options options, final ErrorHandler errorHandler) {
		annotateFactory(outline.getAllPackageContexts());
		annotateClasses(outline.getClasses());
		return true;
	}
}
