package com.dvp.app.mapper;

import com.dvp.infra.adapter.db.entites.TicketsDto;
import com.dvp.infra.api.router.controller.dto.response.ticket.TicketDto;
import com.dvp.domain.entities.Ticket;
import com.dvp.infra.api.router.controller.dto.response.ticket.TicketDataDto;
import com.dvp.infra.api.router.controller.dto.response.ticket.TicketPaginationDto;
import org.mapstruct.Mapper;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    static List<TicketDataDto> toDto(List<Ticket> tikect){
        if (tikect.isEmpty()) {
            new ArrayList<>();
        }
        return tikect.stream().map(t -> TicketDataDto.builder().userId(t.getUserId())
                .ticketId(t.getTicketId())
                .createAt(t.getCreateAt())
                .updateAt(t.getUpdateAt())
                .description(t.getDescription())
                .status(t.getStatus()).build()).toList();
    }

    static TicketPaginationDto toTicketPaginationDto(List<TicketDataDto> ticketData, String msg, Integer page, Long count){
        TicketPaginationDto ticketDto =  TicketPaginationDto.builder().data(ticketData)
                .page(page+1).totalCount(count).build();
        ticketDto.setCode(String.valueOf(HttpStatus.OK.value()));
        ticketDto.setMessage(msg);

        return ticketDto;
    }

    static TicketDto toTicketDto(List<TicketDataDto> userData, String msg){
        TicketDto ticketDto =  TicketDto.builder().data(userData).build();
        ticketDto.setCode(String.valueOf(HttpStatus.OK.value()));
        ticketDto.setMessage(msg);

        return ticketDto;
    }

    static TicketsDto toTicketsEntityDto(Ticket tikect) {
        if (Objects.isNull(tikect) ){
            return TicketsDto.builder().build();
        }

        return  TicketsDto.builder()
                .ticketId(tikect.getTicketId())
                .userId(tikect.getUserId())
                .createAt(Objects.isNull(tikect.getCreateAt()) ? Timestamp.valueOf(LocalDateTime.now()) : tikect.getCreateAt())
                .updateAt(Timestamp.valueOf(LocalDateTime.now()))
                .description(tikect.getDescription())
                .status(tikect.getStatus()).build();
    }
}
