package es.iesfranciscodelosrios.ryg.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String ex;
	private Object f;

	public RecordNotFoundException(String ex, Object f) {
		super(ex + " - " + f);
		this.ex = ex;
		this.f = f;
	}

	public String getExceptionDetail() {
		return ex;
	}

	public Object getFieldValue() {
		return f;
	}

}
