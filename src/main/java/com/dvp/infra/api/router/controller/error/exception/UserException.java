package com.dvp.infra.api.router.controller.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserException extends Exception {
    private final String code;
    private final String message;
}
