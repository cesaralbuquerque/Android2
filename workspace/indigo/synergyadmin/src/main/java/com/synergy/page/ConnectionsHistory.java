package com.synergy.page;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.app.QwicketSession;
import com.synergy.model.Company;
import com.synergy.model.Purchase;
import com.synergy.service.CompanyService;

public class ConnectionsHistory extends MemberBasePage {

	@SpringBean
	CompanyService clientService;

	public ConnectionsHistory() {
		add(new BookmarkablePageLink("planLink", CurrentPlan.class));
		add(new BookmarkablePageLink("buyLink", ProductsPage.class));

		Company client = clientService.find(((QwicketSession) getSession()).getUser().getId());
		ArrayList<Purchase> conns = new ArrayList<Purchase>();
		for (Purchase connection : client.getPurchases()) {
			conns.add(connection);
		}

		ListConns listConns = new ListConns("conns", conns);
		add(listConns);
		add(new PagingNavigator("navegacao", listConns));

	}

	private class ListConns extends PageableListView {

		public ListConns(String id, List<Purchase> chats) {
			super(id, chats, 10);
		}

		@Override
		protected void populateItem(ListItem item) {
			final Purchase chat = (Purchase) item.getModelObject();
			Date date = chat.getDate();
			item.add(new Label("cdate", date != null ? DateFormat.getDateInstance().format(date) : "null"));
			item.add(new Label("cqtde", chat.getProduct().getName()));
			item.add(new Label("cprice", chat.getQuantity() + "/" + chat.getPriceTotal()));
			item.add(new Label("csubid", chat.getSubscriptionId() == null ? "" : chat.getSubscriptionId().toString()));
			item.add(new Label("cstatus", Purchase.StatusToString(chat.getStatus())));
		}

	}
}
