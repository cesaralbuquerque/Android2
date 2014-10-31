package com.synergy.page;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

public class MultipleSelectDropDown extends Panel {

	public MultipleSelectDropDown(String id, List<GroupWrapper> groupWrappers) {
		super(id);
		// add(new AjaxLink("closeLink") {
		//
		// @Override
		// public void onClick(AjaxRequestTarget ajax) {
		// MultipleSelectDropDown.this.setVisible(false);
		// ajax.addComponent(MultipleSelectDropDown.this);
		// }
		//
		// });

		ListView lv = new ListView("groups", groupWrappers) {

			@Override
			protected void populateItem(ListItem item) {
				GroupWrapper gw = (GroupWrapper) item.getModelObject();
				item.add(new Label("gname", gw.getName()));
				item.add(new AjaxCheckBox("gcheck", new PropertyModel(gw, "selected")) {

					@Override
					protected void onUpdate(AjaxRequestTarget arg0) {

					}

				});

			}

		};
		lv.setReuseItems(true);
		add(lv);
	}

}
