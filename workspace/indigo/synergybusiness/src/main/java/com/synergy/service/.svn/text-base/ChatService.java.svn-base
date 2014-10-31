package com.synergy.service;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.synergy.model.Chat;
import com.synergy.model.Company;
import com.synergy.model.Subscriber;
import com.synergy.model.User;

public interface ChatService extends BaseService<Chat> {

	@Transactional
	public Chat createChat(Collection<User> users, String msg, Company client);

	public Collection<Chat> searchChat(Company client, Subscriber subscriber, String text);

	public Collection<Chat> findAll();

	/*
	 * @Transactional Chat createChat();
	 * 
	 * Chat findChat(Long id);
	 * 
	 * UserChat findUserChat(Long id);
	 * 
	 * @Transactional UserChat inviteChat(Long toUser, UserPresenceService
	 * presenceService, UserPendingService pendingService) throws
	 * UserUnavailableException;
	 * 
	 * @Transactional UserChat acceptChat(Chat chat, Long toUser,
	 * UserPresenceService presenceService, UserPendingService pendingService)
	 * throws UserUnavailableException;
	 * 
	 * @Transactional void rejectChat(Chat chat, Long toUser,
	 * UserPresenceService presenceService, UserPendingService pendingService);
	 * 
	 * void joinChat(UserChat user, Chat chat, UserPendingService
	 * pendingService);
	 * 
	 * @Transactional void leaveChat(UserChat user, UserPresenceService
	 * presenceService, UserPendingService pendingService);
	 * 
	 * void shareFile(UserChat user, String fileId, String fileName, String
	 * owner, String contentType, UserPresenceService
	 * presenceService,UserPendingService pendingService);
	 */
}
