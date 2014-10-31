package com.synergy.page;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.request.target.basic.RedirectRequestTarget;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.app.QwicketSession;
import com.synergy.model.Company;
import com.synergy.model.GuestChat;
import com.synergy.model.User;
import com.synergy.service.CompanyService;
import com.synergy.service.GuestChatService;

public class EmailLogMemberPage extends MemberBasePage {

	@SpringBean
	GuestChatService guestChatService;

	@SpringBean
	CompanyService companyService;

	public EmailLogMemberPage() {
		add(new BookmarkablePageLink("chatLogsLink", ChatLogMemberPage.class));
		List<GuestChat> usersFromClient = guestChatService.getGuestChatFromClient(getClient());
		HashMap<String, User> emails = new HashMap<String, User>();
		for (GuestChat user : usersFromClient) {
			if (!emails.containsKey(user.getEmail())) {
				emails.put(user.getEmail(), user);
			}
		}
		ArrayList<User> users = new ArrayList<User>();
		users.addAll(emails.values());
		Collections.sort(users, new Comparator<User>() {

			public int compare(User o1, User o2) {
				return o2.getDateCreated().compareTo(o1.getDateCreated());
			}

		});
		listChat.setList(users);
		add(listChat);
		add(new PagingNavigator("navegacao", listChat));
	}

	ListChat listChat = new ListChat("users", new ArrayList<User>());

	private class ListChat extends PageableListView {

		public ListChat(String id, List<User> users) {
			super(id, users, 20);
		}

		@Override
		protected void populateItem(ListItem item) {
			final GuestChat user = (GuestChat) item.getModelObject();
			Date date = user.getDateCreated();
			item.add(new Label("uname", user.getName()));
			item.add(new EmailLink(user.getEmail()));
			// item.add(new Label("uemail", user.getEmail()));
			item.add(new Label("udate", date != null ? DateFormat.getDateInstance().format(date) : "null"));
		}

	}

	private class EmailLink extends Link {

		String email;

		EmailLink(String email) {
			super("emailLink");
			this.email = email;
			add(new Label("uemail", email));
		}

		@Override
		public void onClick() {
			getRequestCycle().setRequestTarget(new RedirectRequestTarget("mailto:" + email));
		}

	}

	private Company getClient() {
		return clientService.find(((QwicketSession) getSession()).getUser().getId());
	}

}
