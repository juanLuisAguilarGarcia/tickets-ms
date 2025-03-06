package com.dvp.app;

import com.dvp.TicketsApplication;
import com.dvp.domain.entities.Ticket;
import com.dvp.domain.enums.StatusEnum;
import com.dvp.domain.port.db.TicketsPortRepository;
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
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TicketsApplication.class)
public class TicketsServiceTest {

    @Autowired
    private TicketsService ticketsService;

    @MockBean
    private TicketsPortRepository ticketsPortRepository;

    @Test
    public void createTicketTestWhenSuccess() throws TicketException {
        Mockito.when(ticketsPortRepository.save(any())).thenReturn(getTicket());

        TicketDto response = ticketsService.createTicket(getTicket());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getData().get(0).getTicketId(), getTicket().getTicketId());
    }

    @Test
    public void createTicketTestWhenDataAccessException() throws TicketException {
        Mockito.when(ticketsPortRepository.save(any())).thenThrow(new RecoverableDataAccessException("jpa error"));

        Assertions.assertThrows(TicketException.class, () -> ticketsService.createTicket(getTicket()));
    }

    @Test
    public void getTicketByIdTestWhenSuccess() throws TicketException {
        Mockito.when(ticketsPortRepository.getTicketById(any())).thenReturn(getTicket());

        TicketDto response = ticketsService.getTicketById(1L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getData().get(0).getTicketId(), getTicket().getTicketId());
    }

    @Test
    public void getTicketByIdTestWhenDataAccessException() throws TicketException {
        Mockito.when(ticketsPortRepository.getTicketById(any())).thenThrow(new RecoverableDataAccessException("jpa error"));

        Assertions.assertThrows(TicketException.class, () -> ticketsService.getTicketById(1L));
    }

    @Test
    public void getTicketsTestWhenSuccess() throws TicketException {
        Mockito.when(ticketsPortRepository.getTickets(any())).thenReturn(Collections.singletonList(getTicket()));
        Mockito.when(ticketsPortRepository.getCount()).thenReturn(1L);

        TicketPaginationDto response = ticketsService.getTickets(1, 1);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getData().get(0).getTicketId(), getTicket().getTicketId());
    }

    @Test
    public void getTicketsTestWhenDataAccessException() throws TicketException {
        Mockito.when(ticketsPortRepository.getTickets(any())).thenThrow(new RecoverableDataAccessException("jpa error"));

        Assertions.assertThrows(TicketException.class, () -> ticketsService.getTickets(1, 1));
    }

    @Test
    public void deleteTicketTestWhenSuccess() throws TicketException {
        Mockito.when(ticketsPortRepository.getTicketById(any())).thenReturn(getTicket());

        ticketsService.deleteTicket(1L);
    }

    @Test
    public void deleteTicketTestWhenDataAccessException() throws TicketException {
        Mockito.when(ticketsPortRepository.getTicketById(any())).thenThrow(new RecoverableDataAccessException("jpa error"));

        Assertions.assertThrows(TicketException.class, () -> ticketsService.deleteTicket(1L));
    }

    @Test
    public void updateTicketTestWhenSuccess() throws TicketException {
        Mockito.when(ticketsPortRepository.getTicketById(any())).thenReturn(getTicket());
        Mockito.when(ticketsPortRepository.updateTicket(any())).thenReturn(getTicket());

        TicketDto response = ticketsService.updateTicket(getTicket());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getData().get(0).getTicketId(), getTicket().getTicketId());
    }

    @Test
    public void updateTicketTestWhenNotExists() throws TicketException {
        Mockito.when(ticketsPortRepository.getTicketById(any())).thenReturn(Ticket.builder().build());

        Assertions.assertThrows(TicketException.class, () -> ticketsService.updateTicket(getTicket()));
    }

    @Test
    public void updateTicketTestWhenDataAccessException() throws TicketException {
        Mockito.when(ticketsPortRepository.getTicketById(any())).thenThrow(new RecoverableDataAccessException("jpa error"));

        Assertions.assertThrows(TicketException.class, () -> ticketsService.updateTicket(getTicket()));
    }

    @Test
    public void getByFilterTestWhenSuccess() throws TicketException {
        Mockito.when(ticketsPortRepository.getTicketByFilter(any(), any())).thenReturn(Collections.singletonList(getTicket()));

        TicketDto response = ticketsService.getByFilter(1L, StatusEnum.ABIERTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getData().get(0).getTicketId(), getTicket().getTicketId());
    }

    @Test
    public void getByFilterTestWhenNotValidFilter() throws TicketException {
        Mockito.when(ticketsPortRepository.getTicketByFilter(any(), any())).thenReturn(Collections.singletonList(getTicket()));

        Assertions.assertThrows(TicketException.class, () -> ticketsService.getByFilter(null, null));
    }

    @Test
    public void getByFilterTestWhenDataAccessException() throws TicketException {
        Mockito.when(ticketsPortRepository.getTicketByFilter(any(), any())).thenThrow(new RecoverableDataAccessException("jpa error"));

        Assertions.assertThrows(TicketException.class, () -> ticketsService.getByFilter(1L, StatusEnum.ABIERTO));
    }

    private Ticket getTicket() {
        return Ticket.builder()
                .description("description")
                .status(StatusEnum.ABIERTO)
                .updateAt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .createAt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .userId(1L)
                .ticketId(1L).build();
    }
}
