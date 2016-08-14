package com.eptd.dminer.junit;

import org.junit.Test;

import com.eptd.dminer.core.Authorization;
import com.eptd.dminer.core.Configuration;
import com.eptd.dminer.processor.ProjectLogger;

import junit.framework.Assert;

public class AuthTester {
	
	@Test
	public void testCreateNRevokeOAuthToken() {
		Authorization auth = new Authorization(
				new ProjectLogger("https://api.github.com/repos/qiaozhang/junit-tester",
						Configuration.getDefaultConfig()))
				.createOAuthToken();
		Assert.assertNotNull(auth);
		Assert.assertTrue(auth.revokeOAuthToken());
	}

}
