package com.dvp.infra.api.router.controller.error.handler;

import com.dvp.infra.api.router.controller.error.exception.UserException;
import com.dvp.infra.api.router.controller.dto.GenericResponseDTO;
import com.dvp.infra.api.router.controller.error.ErrorConsts;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<GenericResponseDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException ex ){
        List<String> errores = ex.getBindingResult().getFieldErrors().stream().map(
                x -> String.format("%s -> %s", x.getField(), x.getDefaultMessage())).toList();

        GenericResponseDTO errorMesage = new GenericResponseDTO(
                Integer.toString(HttpStatus.BAD_REQUEST.value()),
                errores.toString()
        );

        return new ResponseEntity<>(errorMesage,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<GenericResponseDTO> handleServletRequestBindingException(ServletRequestBindingException ex ){
        GenericResponseDTO errorMesage = new GenericResponseDTO(
                Integer.toString(HttpStatus.BAD_REQUEST.value()),
                ErrorConsts.ERROR_PARAMETROS_CABECERA
        );

        return new ResponseEntity<>(errorMesage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<GenericResponseDTO> genericException(UserException ex ){
        GenericResponseDTO errorMesage = new GenericResponseDTO(
                ex.getCode(),
                ex.getMessage()
        );

        return new ResponseEntity<>(errorMesage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<GenericResponseDTO> exception(Exception ex ){
        GenericResponseDTO errorMesage = new GenericResponseDTO(
                Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                ex.getMessage()
        );

        return new ResponseEntity<>(errorMesage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
