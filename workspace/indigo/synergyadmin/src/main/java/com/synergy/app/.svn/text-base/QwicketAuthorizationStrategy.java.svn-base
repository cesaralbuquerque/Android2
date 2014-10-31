package com.synergy.app;

import org.apache.wicket.Component;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authorization.strategies.CompoundAuthorizationStrategy;

public class QwicketAuthorizationStrategy extends CompoundAuthorizationStrategy {
	/**
	 * Checks that pages marked with {@link Authorized} have a valid user.
	 */
	@SuppressWarnings("unchecked")
	private static final class ProtectedComponentChecker implements IAuthorizationStrategy {
		public boolean isInstantiationAuthorized(final Class componentClass) {
//			TODO-Commented by Fabiano Modos
//			if (componentClass.getAnnotation(Authorized.class) != null && ((QwicketSession) QwicketSession.get()).getUser() == null) {
//				throw new RestartResponseAtInterceptPageException(HomePage.class);
//			}
			return true;
		}

		public boolean isActionAuthorized(final Component component, final Action action) {
			return true;
		}
	}

	public QwicketAuthorizationStrategy() {
		add(new ProtectedComponentChecker());
	}
}
