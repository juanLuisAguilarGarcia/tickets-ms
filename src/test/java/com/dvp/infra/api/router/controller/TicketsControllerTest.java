package com.dvp.infra.api.router.controller;

import com.dvp.TicketsApplication;
import com.dvp.app.TicketsService;
import com.dvp.domain.enums.StatusEnum;
import com.dvp.infra.api.router.controller.dto.GenericResponseDTO;
import com.dvp.infra.api.router.controller.dto.request.CreateAndUpdateTicketDto;
import com.dvp.infra.api.router.controller.dto.response.ticket.TicketDataDto;
import com.dvp.infra.api.router.controller.dto.response.ticket.TicketDto;
import com.dvp.infra.api.router.controller.dto.response.ticket.TicketPaginationDto;
import com.dvp.infra.api.router.controller.error.exception.TicketException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;

import static com.dvp.infra.api.router.RouterConsts.MSG_CONFIRMATION_DELETE;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TicketsApplication.class)
public class TicketsControllerTest {

    @Autowired
    private TicketsController ticketsController;

    @MockBean
    private TicketsService ticketsService;

    @Test
    public void createTicketTestWhenSuccess() throws TicketException {
        Mockito.when(ticketsService.createTicket(any())).thenReturn(getTicketDto());

        ResponseEntity<TicketDto> response = ticketsController.createTicket(getCreateAndUpdateTicketDto());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Objects.requireNonNull(response.getBody()).getData().get(0).getTicketId(),
                getTicketDto().getData().get(0).getTicketId());
    }

    @Test
    public void createTicketTestWhenException() throws TicketException {
        Mockito.when(ticketsService.createTicket(any())).thenThrow(new TicketException("422-1", "error"));

        Assertions.assertThrows(TicketException.class, () -> ticketsController.createTicket(getCreateAndUpdateTicketDto()));
    }

    @Test
    public void getTikectByIdTestWhenSuccess() throws TicketException {
        Mockito.when(ticketsService.getTicketById(any())).thenReturn(getTicketDto());

        ResponseEntity<TicketDto> response = ticketsController.getTikectById(1L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Objects.requireNonNull(response.getBody()).getData().get(0).getTicketId(),
                getTicketDto().getData().get(0).getTicketId());
    }

    @Test
    public void getTikectsTestWhenSuccess() throws TicketException {
        Mockito.when(ticketsService.getTickets(any(), any())).thenReturn(getTicketPaginationDto());

        ResponseEntity<TicketPaginationDto> response = ticketsController.getTikects(1, 1);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Objects.requireNonNull(response.getBody()).getData().get(0).getTicketId(),
                getTicketDto().getData().get(0).getTicketId());
    }

    @Test
    public void deleteTicketTestWhenSuccess() throws TicketException {
        ResponseEntity<GenericResponseDTO> response = ticketsController.deleteTicket(1L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getBody().getCode(), String.valueOf(HttpStatus.OK.value()));
        Assertions.assertEquals(response.getBody().getMessage(), MSG_CONFIRMATION_DELETE);
    }

    @Test
    public void updateTicketTestWhenSuccess() throws TicketException {
        Mockito.when(ticketsService.updateTicket(any())).thenReturn(getTicketDto());

        ResponseEntity<TicketDto> response = ticketsController.updateTicket(1L, getCreateAndUpdateTicketDto());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Objects.requireNonNull(response.getBody()).getData().get(0).getTicketId(),
                getTicketDto().getData().get(0).getTicketId());
    }

    @Test
    public void getByFilterTestWhenSuccess() throws TicketException {
        Mockito.when(ticketsService.getByFilter(any(), any())).thenReturn(getTicketDto());

        ResponseEntity<TicketDto> response = ticketsController.getByFilter(1L, StatusEnum.ABIERTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Objects.requireNonNull(response.getBody()).getData().get(0).getTicketId(),
                getTicketDto().getData().get(0).getTicketId());
    }

    private TicketDto getTicketDto() {
        return  TicketDto.builder()
                .data(Collections.singletonList(TicketDataDto.builder()
                        .status(StatusEnum.ABIERTO)
                        .description("description")
                        .ticketId(1L)
                        .updateAt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                        .createAt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                        .ticketId(1L).build())).build();
    }

    CreateAndUpdateTicketDto getCreateAndUpdateTicketDto(){
        return CreateAndUpdateTicketDto.builder()
                .userId(1L)
                .description("description")
                .status(StatusEnum.ABIERTO)
                .build();
    }

    TicketPaginationDto getTicketPaginationDto(){
        return TicketPaginationDto.builder()
                .page(1)
                .totalCount(1L)
                .data(Collections.singletonList(TicketDataDto.builder()
                        .status(StatusEnum.ABIERTO)
                        .description("description")
                        .ticketId(1L)
                        .updateAt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                        .createAt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                        .ticketId(1L).build())).build();
    }
}
