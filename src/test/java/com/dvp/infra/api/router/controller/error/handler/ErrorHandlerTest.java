package com.dvp.infra.api.router.controller.error.handler;

import com.dvp.infra.api.router.controller.TicketsController;
import com.dvp.infra.api.router.controller.dto.GenericResponseDTO;
import com.dvp.infra.api.router.controller.dto.request.CreateAndUpdateTicketDto;
import com.dvp.infra.api.router.controller.error.exception.TicketException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ErrorHandlerTest {
    private ErrorHandler errorHandler;
    private TicketsController ticketsController;
    private DataBinder dataBinder;
    private WebRequest request;

    @Before
    public void init(){
        errorHandler = new ErrorHandler();
        ticketsController = new TicketsController();
        dataBinder = new DataBinder(ticketsController);
        request = Mockito.mock(WebRequest.class);
    }

    @Test
    public void handleMethodArgumentNotValidTest() throws NoSuchMethodException{
        MethodArgumentNotValidException methodArgumentNotValidException = new MethodArgumentNotValidException(
                new MethodParameter(TicketsController.class.getMethod("createTicket", CreateAndUpdateTicketDto.class),-1),
                dataBinder.getBindingResult());
        assertNotNull(errorHandler.handleMethodArgumentNotValid(methodArgumentNotValidException ));
    }

    @Test
    public void handleServletRequestBindingExceptionTest() throws NoSuchMethodException {
        MissingPathVariableException missingPathVariableException = new MissingPathVariableException("satellite_name",
                new MethodParameter(TicketsController.class.getMethod("createTicket", CreateAndUpdateTicketDto.class),-1));
        assertNotNull(errorHandler.handleServletRequestBindingException(missingPathVariableException ));
    }

    @Test
    public void genericExceptionTest() {
        ResponseEntity<GenericResponseDTO> response = errorHandler.genericException(new TicketException("422-1", "error"));

        assertNotNull(response);
        assertEquals("422-1", response.getBody().getCode());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    public void exceptionTest() {
        ResponseEntity<GenericResponseDTO> response = errorHandler.exception(new Exception( "error"));

        assertNotNull(response);
        assertEquals("error", response.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void httpMessageNotReadableExceptionTest() {
        ResponseEntity<GenericResponseDTO> response = errorHandler.httpMessageNotReadableException(new HttpMessageNotReadableException( "com.dvp.domain.enums.StatusEnum"));

        assertNotNull(response);
        assertEquals("Status value not allowed, accepted : [ABIERTO, CERRADO]", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void methodArgumentTypeMismatchExceptionTest() throws NoSuchMethodException {
        ResponseEntity<GenericResponseDTO> response = errorHandler.methodArgumentTypeMismatchException(
                new MethodArgumentTypeMismatchException(null, null, "name",
                        new MethodParameter(TicketsController.class.getMethod("createTicket", CreateAndUpdateTicketDto.class),-1) , null));

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}

