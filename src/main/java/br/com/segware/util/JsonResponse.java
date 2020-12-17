package br.com.segware.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class JsonResponse {
	
	public static ResponseEntity<Object> build(HttpStatus status, String message) {
		Map<String, Object> response = new HashMap<>();
		response.put("status", status);
		response.put("message", message);
		
		return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(response);
	}
}
