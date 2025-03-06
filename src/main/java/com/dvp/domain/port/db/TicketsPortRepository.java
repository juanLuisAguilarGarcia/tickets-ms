package com.dvp.domain.port.db;

import com.dvp.domain.entities.Ticket;
import com.dvp.domain.enums.StatusEnum;
import com.dvp.infra.adapter.db.entites.TicketsDto;
import com.dvp.infra.api.router.controller.error.exception.TicketException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketsPortRepository {
    Ticket save(TicketsDto ticket);
    Ticket getTicketById(Long ticketId);
    List<Ticket> getTickets(Pageable pageable);
    Long getCount() throws TicketException;
    void deleteTicket(Long ticketId);
    List<Ticket> getTicketByFilter(Long userId, StatusEnum status);
    Ticket updateTicket(TicketsDto ticket );
}
