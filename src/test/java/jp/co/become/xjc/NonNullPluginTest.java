package jp.co.become.xjc;

import org.junit.Assert;
import org.junit.Test;

public class NonNullPluginTest {

	@Test
	public void testInit() {
		Assert.assertNotNull(NonNullPlugin.class);
	}
}
