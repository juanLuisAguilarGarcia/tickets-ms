package com.dvp.infra.adapter.db;

import com.dvp.TicketsApplication;
import com.dvp.domain.entities.Ticket;
import com.dvp.domain.enums.StatusEnum;
import com.dvp.infra.adapter.db.entites.TicketsDto;
import com.dvp.infra.adapter.db.jpa.TicketsJpaRepository;
import com.dvp.infra.adapter.db.jpa.TicketsPaginationJpaRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TicketsApplication.class)
public class TicketsRepositoryTest {

    @Autowired
    private TicketsRepository ticketsRepository;

    @MockBean
    private TicketsJpaRepository ticketsJpaRepository;

    @MockBean
    private TicketsPaginationJpaRepository ticketsPaginationJpaRepository;

    @Test
    public void saveTestWhenSuccess()   {
        Mockito.when(ticketsJpaRepository.save(any())).thenReturn(getTicketsDto());

        Ticket response = ticketsRepository.save(getTicketsDto());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getTicketId(), getTicketsDto().getTicketId());
    }

    @Test
    public void getTicketByIdTestWhenSuccess()  {
        Mockito.when(ticketsJpaRepository.findById(any())).thenReturn(Optional.ofNullable(getTicketsDto()));

        Ticket response = ticketsRepository.getTicketById(1L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getTicketId(), getTicketsDto().getTicketId());
    }

    @Test
    public void getTicketsTestWhenSuccess()  {
        Pageable pagination = PageRequest.of(1, 1, Sort.by("ticketId").ascending());

        Page<TicketsDto> iterable = new PageImpl<TicketsDto>(
                Collections.singletonList(getTicketsDto()));

        Mockito.when(ticketsPaginationJpaRepository.findAll(eq(pagination))).thenReturn(iterable);

        List<Ticket> response = ticketsRepository.getTickets(pagination);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.get(0).getTicketId(), getTicketsDto().getTicketId());
    }

    @Test
    public void getCountTestWhenSuccess()  {
        Mockito.when(ticketsJpaRepository.count()).thenReturn(1L);

        Long response = ticketsRepository.getCount();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response, 1L);
    }

    @Test
    public void deleteTicketTestWhenSuccess()  {
        ticketsRepository.deleteTicket(1L);
    }

    @Test
    public void updateUserTestWhenSuccess()  {
        Mockito.when(ticketsJpaRepository.save(any())).thenReturn(getTicketsDto());

        Ticket response = ticketsRepository.updateTicket(getTicketsDto());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getTicketId(), getTicketsDto().getTicketId());
    }

    @Test
    public void getTicketByFilterTestWhenSuccess()  {
        Iterable<TicketsDto> iterable = Collections.singletonList(getTicketsDto());

        Mockito.when(ticketsJpaRepository.findByCustomFilter(any(), any())).thenReturn((List<TicketsDto>) iterable);

        List<Ticket> response = ticketsRepository.getTicketByFilter(1L, StatusEnum.ABIERTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.get(0).getTicketId(), getTicketsDto().getTicketId());
    }

    private TicketsDto getTicketsDto() {
        return TicketsDto.builder()
                .description("description")
                .status(StatusEnum.ABIERTO)
                .updateAt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .createAt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .ticketId(1L)
                .userId(1L).build();
    }
}
