package com.gw.web.common.exception;

import com.gw.web.common.model.enums.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Not Found 404
 * 2019/01/09 14:05:30
 * @author Jeremy Zhang
 */
public class NotFoundException extends RestException {

	public NotFoundException(Errors errors) {
		super(errors);
	}

	public NotFoundException(Errors errors, String errorMsg) {
		super(errors, errorMsg);
	}

}
