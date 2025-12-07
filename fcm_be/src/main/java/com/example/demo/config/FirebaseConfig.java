package com.example.demo.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {

	@Value("${firebase.credentials.path}")
	private String credentialPath;

	@PostConstruct
	public void initialize() throws IOException {
		FileInputStream refreshToken = new FileInputStream(credentialPath);

		FirebaseOptions options = FirebaseOptions.builder()
			.setCredentials(GoogleCredentials.fromStream(refreshToken))
			.build();

		FirebaseApp.initializeApp(options);	}
}
