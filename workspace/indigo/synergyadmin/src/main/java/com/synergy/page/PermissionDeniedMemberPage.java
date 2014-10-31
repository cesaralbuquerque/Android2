package com.synergy.page;

import org.apache.wicket.PageParameters;

import com.synergy.app.QwicketSession;
import com.synergy.model.Company;

public class PermissionDeniedMemberPage extends MemberBasePage {

	public PermissionDeniedMemberPage() {
		this(null);
	}

	public PermissionDeniedMemberPage(PageParameters parameters) {
		super();

	}

	@Override
	protected void checkSessionPermission() {
		// nao faz nada
	}

	private Company getClient() {
		return clientService.find(((QwicketSession) getSession()).getUser().getId());
	}

}
