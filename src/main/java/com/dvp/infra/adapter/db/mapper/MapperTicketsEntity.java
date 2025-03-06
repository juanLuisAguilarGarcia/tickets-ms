package com.dvp.infra.adapter.db.mapper;

import com.dvp.domain.entities.Ticket;
import com.dvp.infra.adapter.db.entites.TicketsDto;
import org.mapstruct.Mapper;

import java.util.*;

@Mapper(componentModel = "spring")
public interface MapperTicketsEntity {

    static Ticket toTicket(TicketsDto ticket){
        if (Objects.isNull(ticket)) {
            return Ticket.builder().build();
        }

        return Ticket.builder()
                .ticketId(ticket.getTicketId())
                .userId(ticket.getUserId())
                .createAt(ticket.getCreateAt())
                .updateAt(ticket.getUpdateAt())
                .description(ticket.getDescription())
                .status(ticket.getStatus()).build();
    }

    static Ticket toTicketFromOptional(Optional<TicketsDto> ticket){
        if (ticket.isEmpty()) {
            return Ticket.builder().build();
        }

        TicketsDto u = ticket.get();

        return Ticket.builder()
                .ticketId(u.getTicketId())
                .userId(u.getUserId())
                .createAt(u.getCreateAt())
                .updateAt(u.getUpdateAt())
                .description(u.getDescription())
                .status(u.getStatus()).build();
    }

    static List<Ticket> toTicketList(Iterator<TicketsDto> ticketsDto){
        List<Ticket> tickets = new ArrayList<>();

        while(ticketsDto.hasNext()) {
            TicketsDto ticketDto = ticketsDto.next();
            tickets.add(Ticket.builder().userId(ticketDto.getUserId())
                    .ticketId(ticketDto.getTicketId())
                    .createAt(ticketDto.getCreateAt())
                    .updateAt(ticketDto.getUpdateAt())
                    .description(ticketDto.getDescription())
                    .status(ticketDto.getStatus()).build());
        }

        return tickets;
    }
}
