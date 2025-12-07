package com.example.demo.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FcmService {

	private final FcmTokenService fcmTokenService;

	//특정 토큰으로 알림 전송
	public String sendNotification(String userId, String title, String body) throws FirebaseMessagingException {
		String token = fcmTokenService.findByToken(userId);
		if(token == null){
			return "User not found or token not registered";
		}

		Message message = Message.builder()
			.setNotification(Notification.builder()
				.setTitle(title)
				.setBody(body)
				.build())
			.setToken(token)
			.build();


		String messageId = FirebaseMessaging.getInstance().send(message);
		return "success: " + messageId;
	}

	//등록된 모든 토큰에 알림 전송
	public String sendNotificationToAll(String title, String body) throws FirebaseMessagingException {
		List<String> tokens = fcmTokenService.findAllTokens();
		MulticastMessage message = MulticastMessage.builder()
			.setNotification(Notification.builder()
				.setTitle(title)
				.setBody(body)
				.build())
			.addAllTokens(tokens)
			.build();

		BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
		if (response.getFailureCount() > 0) {
			List<SendResponse> responses = response.getResponses();
			List<String> failedTokens = new ArrayList<>();
			for (int i = 0; i < responses.size(); i++) {
				if (!responses.get(i).isSuccessful()) {
					failedTokens.add(tokens.get(i));
				}
			}
			return "List of tokens that caused failures: " + failedTokens;
		}
		return "All success";
	}
}
