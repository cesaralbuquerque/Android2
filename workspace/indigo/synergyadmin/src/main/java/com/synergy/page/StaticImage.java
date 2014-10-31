package com.synergy.page;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;

public class StaticImage extends WebComponent {

	public StaticImage(String id, IModel model) {
		super(id, model);
	}

	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		checkComponentTag(tag, "img");
		tag.put("src", getModelObject().toString());
	}

}
