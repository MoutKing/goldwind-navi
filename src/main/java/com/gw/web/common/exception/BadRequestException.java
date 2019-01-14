package com.gw.web.common.exception;

import com.gw.web.common.model.enums.Errors;

/**
 * Bad Request 400
 * 2019/01/09 14:03:52
 * @author Jeremy Zhang
 */
public class BadRequestException  extends RestException {
	public BadRequestException(Errors errors) {
		super(errors);
	}

	public BadRequestException(Errors errors, String errorMsg) {
		super(errors, errorMsg);
	}
}
