package com.synergy.page;

public class MemberUserPanel extends UserPanel {

	public MemberUserPanel(String id) {
		super(id);
	}

	@Override
	protected RegisterUserPanel createRegisterUserPanel() {
		return new RegisterUserPanel("addUserPanel", new FormRedirect() {

			public void redirect(Object param) {
				setResponsePage(ContactsMemberPage.class);
			}

		});
	}

}
