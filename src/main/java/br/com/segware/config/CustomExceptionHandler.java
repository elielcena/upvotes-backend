package br.com.segware.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.segware.exception.ErrorResponse;
import br.com.segware.exception.RecordNotFoundException;

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	private String now = LocalDateTime.now().toString();

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(formatarMensagem(ex.getLocalizedMessage()));
		ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, now , "Internal Server Error", details);
		return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(formatarMensagem(ex.getLocalizedMessage()));
		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND, now, "Record Not Found", details);
		return new ResponseEntity(error, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>();
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(formatarMensagem(error.toString().split(":")[0] + " " + error.getDefaultMessage()));
		}
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, now, "Validation Failed", details);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}
	
	private String formatarMensagem(String mensagem) {
		return mensagem.substring(0,1).toUpperCase().concat(mensagem.substring(1).toLowerCase());
	}
}