package com.synergy.page;


public class MemberGroupsPanel extends GroupsPanel {

	public MemberGroupsPanel(String id) {
		super(id);
	}

	@Override
	protected RegisterGroupPanel createRegisterGroupPanel() {
		return new RegisterGroupPanel("addGroupPanel", new FormRedirect() {

			public void redirect(Object param) {
				setResponsePage(GroupsMemberPage.class);
			}

		});
	}

}
