package com.jaw.common.business;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.security.SecurityCheck;

@Component
public class CommonBusiness {
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private ApplicationCache applicationCache;

	@Autowired
	private SecurityCheck havilaSecurityCheck;

	public void changeObject(Object destination, Object source) {

		try {
			BeanUtils.copyProperties(destination, source);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return;
	}

	public String generateRandomString(String userId) {
		int value = (int) (Math.random() * 88477);

		try {
			if (userId.length() > 4) {
				userId = (String) userId.subSequence(0, 5);
			} else {

				int i = 5 - userId.length();
				int count = 0;
				while (count < i) {
					userId = userId.concat("a");
					count++;
				}
			}
		} catch (StringIndexOutOfBoundsException stringIndexOutOfBoundException) {

		}

		return userId + "" + value;

	}

	public String createPassword(String userId) {

		return havilaSecurityCheck.createPassword(generateRandomString(userId));

	}

	public String createPasswordForUser(String password) {

		return havilaSecurityCheck.createPassword(password);

	}

}
