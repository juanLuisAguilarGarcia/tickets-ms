package com.dvp.infra.api.router.controller.mapper;

import com.dvp.infra.api.router.controller.dto.request.CreateAndUpdateTicketDto;
import com.dvp.domain.entities.Ticket;
import org.mapstruct.Mapper;

import java.util.Objects;

@Mapper(componentModel = "spring")
public interface TicketDtoMapper {

    static Ticket toEntity(CreateAndUpdateTicketDto createAndUpdateTicketDto){
        return updateToEntity(createAndUpdateTicketDto, null);
    }

    static Ticket updateToEntity(CreateAndUpdateTicketDto createAndUpdateTicketDto, Long ticketId){
        if(Objects.isNull(createAndUpdateTicketDto)){
            return Ticket.builder().build();
        }

        return Ticket.builder()
                .ticketId(ticketId)
                .userId(createAndUpdateTicketDto.getUserId())
                .description(createAndUpdateTicketDto.getDescription())
                .status(createAndUpdateTicketDto.getStatus()).build();
    }
}
