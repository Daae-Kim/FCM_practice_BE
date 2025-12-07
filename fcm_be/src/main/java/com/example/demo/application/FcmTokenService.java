package com.example.demo.application;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class FcmTokenService {

	private final ConcurrentHashMap<String, String> tokenMap = new ConcurrentHashMap<>();

	//토큰 서버에 저장
	public void saveToken(String userId, String token) {
		tokenMap.put(userId, token);
	}

	//특정 사용자의 토큰 조회
	public String findByToken(String userId) {
		return tokenMap.get(userId);
	}

	//등록된 모든 토큰 조회
	public List<String> findAllTokens(){
		return tokenMap.values().stream().toList();
	}

	//토큰 삭제
	public void removeToken(String userId) {
		tokenMap.remove(userId);
	}

	//전체 토큰 맵 조회
	public Map<String, String> getTokenMap() {
		return tokenMap;
	}
}
