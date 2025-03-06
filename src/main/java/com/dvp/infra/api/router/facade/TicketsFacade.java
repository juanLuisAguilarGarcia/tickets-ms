package com.dvp.infra.api.router.facade;

import com.dvp.domain.enums.StatusEnum;
import com.dvp.infra.api.router.controller.dto.response.ticket.TicketDto;
import com.dvp.infra.api.router.controller.dto.response.ticket.TicketPaginationDto;
import com.dvp.infra.api.router.controller.error.exception.TicketException;
import com.dvp.domain.entities.Ticket;

public interface TicketsFacade {

    public TicketDto createTicket(Ticket ticket) throws TicketException;

    public TicketDto getTicketById(Long ticketId) throws TicketException;

    public TicketPaginationDto getTickets(Integer page, Integer size) throws TicketException;

    public void deleteTicket(Long ticketId) throws TicketException;

    public TicketDto updateTicket(Ticket ticket) throws TicketException;

    public TicketDto getByFilter(Long userId, StatusEnum status) throws TicketException;
}
