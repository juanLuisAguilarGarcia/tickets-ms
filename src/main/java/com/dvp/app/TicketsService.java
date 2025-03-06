package com.dvp.app;

import com.dvp.app.mapper.TicketMapper;
import com.dvp.domain.entities.Ticket;
import com.dvp.domain.enums.StatusEnum;
import com.dvp.domain.port.cache.CachePortRepository;
import com.dvp.domain.port.db.TicketsPortRepository;
import com.dvp.infra.adapter.cache.CacheRepository;
import com.dvp.infra.api.router.controller.dto.response.ticket.TicketDto;
import com.dvp.infra.api.router.controller.dto.response.ticket.TicketPaginationDto;
import com.dvp.infra.api.router.controller.error.exception.TicketException;
import com.dvp.infra.api.router.facade.TicketsFacade;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.dvp.app.ServiceConsts.*;

@Slf4j
@Service
public class TicketsService implements TicketsFacade {

    @Autowired
    private CachePortRepository cachePortRepository;

    @Autowired
    private TicketsPortRepository ticketsPortRepository;

    public TicketDto createTicket(Ticket ticketToSave) throws TicketException {
        try{
            Ticket ticket = ticketsPortRepository.save(TicketMapper.toTicketsEntityDto(ticketToSave));

            log.info(String.format(MSG_PROCESS_SERVICE, "created", "userId: ", ticketToSave.getUserId()));
            return TicketMapper.toTicketDto(TicketMapper.toDto(Collections.singletonList(ticket)), MSG_TICKET_CREATED);
        } catch(DataAccessException ex){
            log.error(String.format(MSG_ERROR_PROCESS_SERVICE, "created",  "userId: ", ticketToSave.getUserId(),
                    ex.getMessage()));
            throw new TicketException("422-1", ex.getMessage());
        }
    }

    public TicketDto getTicketById(Long ticketId) throws TicketException {
        try{
            Ticket ticket = existsTicket(ticketId);
            log.info(String.format(MSG_PROCESS_SERVICE, "getById", "ticketId: ", ticketId));
            return TicketMapper.toTicketDto(TicketMapper.toDto(Collections.singletonList(ticket)), MSG_TICKET_GET);
        } catch(DataAccessException ex){
            log.error(String.format(MSG_ERROR_PROCESS_SERVICE, "getById",  "ticketId: ", ticketId,
                    ex.getMessage()));
            throw new TicketException("422-2", ex.getMessage());
        }

    }

    public TicketPaginationDto getTickets(Integer page, Integer size) throws TicketException {
        try{
            Pageable pageable = PageRequest.of(page, size, Sort.by("ticketId").ascending());

            List<Ticket> tickets = ticketsPortRepository.getTickets(pageable);
            Long count = ticketsPortRepository.getCount();

            log.info(String.format(MSG_PROCESS_SERVICE, "getTickets", "page: ", page));
            return TicketMapper.toTicketPaginationDto(TicketMapper.toDto(tickets), MSG_TICKET_GET_ALL, page, count);
        } catch(DataAccessException ex){
            log.error(String.format(MSG_ERROR_PROCESS_SERVICE, "getTickets",  "page: ", page,
                    ex.getMessage()));
            throw new TicketException("422-3", ex.getMessage());
        }

    }

    public void deleteTicket(Long ticketId) throws TicketException {
        try{
            existsTicket(ticketId);

            ticketsPortRepository.deleteTicket(ticketId);
            log.info(String.format(MSG_PROCESS_SERVICE, "delete", "ticketId: ", ticketId));
            return;
        } catch(DataAccessException ex){
            log.error(String.format(MSG_ERROR_PROCESS_SERVICE, "delete",  "ticketId: ", ticketId,
                    ex.getMessage()));
            throw new TicketException("422-4", ex.getMessage());
        }
    }

    public TicketDto updateTicket(Ticket ticketToUpdate) throws TicketException {
        try{
            Ticket ticketExists = existsTicket(ticketToUpdate.getTicketId());

            ticketToUpdate.setCreateAt(ticketExists.getCreateAt());
            Ticket ticket = ticketsPortRepository.updateTicket(TicketMapper.toTicketsEntityDto(ticketToUpdate));
            log.info(String.format(MSG_PROCESS_SERVICE, "update", "ticketId: ", ticketToUpdate.getTicketId()));
            return TicketMapper.toTicketDto(TicketMapper.toDto(Collections.singletonList(ticket)), MSG_TICKET_UPDATE);
        } catch(DataAccessException ex){
            log.error(String.format(MSG_ERROR_PROCESS_SERVICE, "update",  "ticketId: ", ticketToUpdate.getTicketId(),
                    ex.getMessage()));
            throw new TicketException("422-5", ex.getMessage());
        }
    }

    public TicketDto getByFilter(Long userId, StatusEnum status) throws TicketException {
        try{
            isValidFilter(userId, status);

            List<Ticket> tickets = getTicketsFromCacheOrRepository(userId, status);

            log.info(String.format(MSG_PROCESS_SERVICE, "getByFilter", "userId: ", userId));
            return TicketMapper.toTicketDto(TicketMapper.toDto(tickets), MSG_TICKET_GET_ALL);
        } catch(DataAccessException ex){
            log.error(String.format(MSG_ERROR_PROCESS_SERVICE, "getByFilter",  "userId: ", userId,
                    ex.getMessage()));
            throw new TicketException("422-6", ex.getMessage());
        }

    }

    private Ticket existsTicket(Long ticketId) throws TicketException {
        Ticket ticket = ticketsPortRepository.getTicketById(ticketId);

        if(Objects.isNull(ticket.getTicketId())  || ticket.getTicketId() < 1L) {
            log.error(String.format(MSG_ERROR_PROCESS_SERVICE, "exists",  "ticketId: ", ticketId,
                    "Ticket not found"));
            throw new TicketException("422-7", "ticket not found");
        }

        return ticket;
    }

    private void isValidFilter(Long userId, StatusEnum status ) throws TicketException {
        if((Objects.isNull(userId) || userId < 1L ) && Objects.isNull(status)) {
            log.error(String.format(MSG_ERROR_PROCESS_SERVICE, "exists",  "userId: ", userId,
                    "Not found filter."));
            throw new TicketException("422-8", "Not found filter");
        }

        return;
    }

    private List<Ticket> getTicketsFromCacheOrRepository(Long userId, StatusEnum status) throws TicketException {
        List<Ticket> tickets;

        String ticketCache = cachePortRepository.get(String.valueOf(userId));

        boolean isCacheable = !Objects.isNull(userId) && Objects.isNull(status);

        if(!Objects.isNull(ticketCache) &&
                !ticketCache.trim().isEmpty() && isCacheable){
            tickets = Arrays.stream(new Gson().fromJson(ticketCache, Ticket[].class)).toList();
        } else {
            tickets = ticketsPortRepository.getTicketByFilter(userId, status);;
        }

        if((Objects.isNull(ticketCache) || ticketCache.trim().isEmpty())
                && isCacheable){
            cachePortRepository.set(String.valueOf(userId), new Gson().toJson(tickets));
        }

        return tickets;
    }
}
