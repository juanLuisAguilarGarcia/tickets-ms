package com.dvp.infra.api.router.controller.error.handler;

import com.dvp.infra.api.router.controller.error.exception.TicketException;
import com.dvp.infra.api.router.controller.dto.GenericResponseDTO;
import com.dvp.infra.api.router.controller.error.ErrorConsts;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @ExceptionHandler({TicketException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<GenericResponseDTO> genericException(TicketException ex ){
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

    @ExceptionHandler({HttpMessageNotReadableException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<GenericResponseDTO> httpMessageNotReadableException(HttpMessageNotReadableException ex ){
        return new ResponseEntity<>(getGenericResponseWithMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<GenericResponseDTO> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex ){
        return new ResponseEntity<>(getGenericResponseWithMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private GenericResponseDTO getGenericResponseWithMessage(String message){
        GenericResponseDTO errorMesage;
        if (message.contains("com.dvp.domain.enums.StatusEnum")) {
            errorMesage = new GenericResponseDTO(
                    Integer.toString(HttpStatus.BAD_REQUEST.value()),
                    "Status value not allowed, accepted : [ABIERTO, CERRADO]");
        } else {
            errorMesage = new GenericResponseDTO(
                    Integer.toString(HttpStatus.BAD_REQUEST.value()),
                    message);
        }

        return errorMesage;
    }

}
