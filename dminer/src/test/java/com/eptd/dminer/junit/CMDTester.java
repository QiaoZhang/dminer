package com.eptd.dminer.junit;

import java.io.IOException;

import org.junit.Test;

import com.eptd.dminer.processor.CMDProcessor;

import junit.framework.Assert;

public class CMDTester {

	@Test
	public void testExecute() throws InterruptedException, IOException {
		Assert.assertEquals(0, new CMDProcessor().addCommand("echo Hello World!").execute());
	}

}
