package com.synergy.page;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.app.QwicketSession;
import com.synergy.model.Chat;
import com.synergy.model.Company;
import com.synergy.model.Subscriber;
import com.synergy.model.User;
import com.synergy.service.ChatService;
import com.synergy.service.CompanyService;
import com.synergy.service.SubscriberService;

public class ChatLogMemberPage extends MemberBasePage {

	@SpringBean
	ChatService chatService;

	@SpringBean
	CompanyService clientService;

	@SpringBean
	SubscriberService subscriberService;

	ViewChatLog panelChatLog = new ViewChatLog("panelChatLog");

	public ChatLogMemberPage() {
		add(new BookmarkablePageLink("emailLogsLink", EmailLogMemberPage.class));
		add(new ChatSearchForm("formSearch"));
		panelChatLog.setVisible(false);
		add(panelChatLog);
	}

	// TODO - it need to change, can't be a link... find one group component
	private class ViewChatLog extends Form {

		private String chatLog;

		public ViewChatLog(String id) {
			super(id);
			setModel(new CompoundPropertyModel(this));
			add(new MultiLineLabel("chatLog"));
			add(new Link("closeViewChatLog") {

				@Override
				public void onClick() {
					panelChatLog.setVisible(false);
				}

			});
		}

		public void setChatLog(String chatLog) {
			this.chatLog = chatLog;
		}

		public String getChatLog() {
			return chatLog;
		}

	}

	private class ChatSearchForm extends Form {

		private User user;
		private String text;
		ListChat listChat = new ListChat("chats", new ArrayList<Chat>());

		public ChatSearchForm(String id) {
			super(id);
			setModel(new CompoundPropertyModel(this));
			add(new TextField("text"));
			DropDownChoice ddc = new DropDownChoice("user");
			add(ddc);
			List<Subscriber> users = subscriberService.subscribersFromClient(getClient());

			/*
			 * new ArrayList<User>(); for (Group group :
			 * getClient().getGroups()) { Collection<User> groups =
			 * group.getUsers(); for (User user : groups) { if (user instanceof
			 * Subscriber && !users.contains(user)) { users.add(user); } } }
			 */

			ddc.setChoices(users);
			ddc.setChoiceRenderer(new IChoiceRenderer() {

				public Object getDisplayValue(Object object) {
					return ((User) object).getName();
				}

				public String getIdValue(Object object, int index) {
					return ((User) object).getId().toString();
				}

			});

			add(listChat);
			add(new PagingNavigator("navegacao", listChat));
		}

		private class ListChat extends PageableListView {

			public ListChat(String id, List<Chat> chats) {
				super(id, chats, 10);
			}

			@Override
			protected void populateItem(ListItem item) {
				final Chat chat = (Chat) item.getModelObject();
				Date date = chat.getDate();
				item.add(new Label("cdate", date != null ? DateFormat.getDateInstance().format(date) : "null"));
				String chatlog = chat.getChatlog();
				if (chatlog == null) {
					chatlog = "";
				}
				int to = chatlog.length();
				item.add(new Label("clog", chatlog.substring(0, to > 60 ? 60 : to)));
				item.add(new Link("viewChatLog") {

					@Override
					public void onClick() {
						// panelChatLog.setChatLog(chat.getChatlog().replaceAll("\n",
						// "<br>"));
						panelChatLog.setChatLog(chat.getChatlog());
						panelChatLog.setVisible(true);
					}
				});
			}

		}

		@Override
		protected void onSubmit() {
			Collection<Chat> ret = chatService.searchChat(getClient(), (Subscriber) user, text);
			listChat.setList((List) ret);
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public User getUser() {
			return user;
		}

	}

	private Company getClient() {
		return clientService.find(((QwicketSession) getSession()).getUser().getId());
	}

}
