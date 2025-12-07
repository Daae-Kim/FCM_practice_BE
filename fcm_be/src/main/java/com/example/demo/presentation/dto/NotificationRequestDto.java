package com.example.demo.presentation.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class NotificationRequestDto {
	//알림 전송 요청 dto
	private String userId;
	private String title;
	private String body;
}
