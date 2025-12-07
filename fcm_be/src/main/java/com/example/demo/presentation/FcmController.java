package com.example.demo.presentation;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.FcmService;
import com.example.demo.application.FcmTokenService;
import com.example.demo.presentation.dto.NotificationRequestDto;
import com.example.demo.presentation.dto.TokenRegistrationDto;
import com.google.firebase.messaging.FirebaseMessagingException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fcm")
@RequiredArgsConstructor
public class FcmController {

	private final FcmService fcmService;
	private final FcmTokenService fcmTokenService;

	//토큰 등록
	@PostMapping("/register")
	public String registerToken(@RequestBody TokenRegistrationDto tokenRegistrationDto) {
		fcmTokenService.saveToken(tokenRegistrationDto.getUserId(), tokenRegistrationDto.getToken());
		return "토큰 등록 성공";
	}
	//특정 사용자에게 알림 전송
	@PostMapping("/send")
	public String sendNotification(@RequestBody NotificationRequestDto notificationRequestDto) throws
		FirebaseMessagingException {
		return fcmService.sendNotification(notificationRequestDto.getUserId(),
			notificationRequestDto.getTitle(), notificationRequestDto.getBody());
	}

	//모든 사용자에게 알림 전송
	@PostMapping("/send-all")
	public String sendNotificationAll(@RequestBody NotificationRequestDto notificationRequestDto) throws
		FirebaseMessagingException{
			return fcmService.sendNotificationToAll(notificationRequestDto.getTitle(), notificationRequestDto.getBody());
	}

	@GetMapping("/tokens/count")
	public int getTokenCount(){
		return fcmTokenService.findAllTokens().size();
	}

	@ExceptionHandler(FirebaseMessagingException.class)
	public String handleFirebaseException(FirebaseMessagingException e) {
		return "Failed to send notification: " + e.getMessage();
	}


}
