package com.gw.web.common.exception;

import com.gw.web.common.model.enums.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Forbidden 403
 * 2019/01/09 14:06:30
 * @author Jeremy Zhang
 */
public class ForbiddenException extends RestException {

	public ForbiddenException(Errors errors) {
		super(errors);
	}

	public ForbiddenException(Errors errors, String errorMsg) {
		super(errors, errorMsg);
	}
}
