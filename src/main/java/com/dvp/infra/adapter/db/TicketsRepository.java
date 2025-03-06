package com.dvp.infra.adapter.db;

import com.dvp.domain.enums.StatusEnum;
import com.dvp.domain.port.db.TicketsPortRepository;
import com.dvp.infra.adapter.db.entites.TicketsDto;
import com.dvp.infra.adapter.db.jpa.TicketsJpaRepository;
import com.dvp.infra.adapter.db.jpa.TicketsPaginationJpaRepository;
import com.dvp.infra.adapter.db.mapper.MapperTicketsEntity;
import com.dvp.domain.entities.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketsRepository implements TicketsPortRepository {

    @Autowired
    private TicketsJpaRepository ticketsJpaRepository;

    @Autowired
    private TicketsPaginationJpaRepository ticketsPaginationJpaRepository;

    @Transactional
    public Ticket save(TicketsDto ticketToSave){
        return MapperTicketsEntity.toTicket(ticketsJpaRepository.save(ticketToSave));
    }

    public Ticket getTicketById(Long ticketId){
        return MapperTicketsEntity.toTicketFromOptional(ticketsJpaRepository.findById(ticketId));
    }

    public List<Ticket> getTickets(Pageable pageable) {
        return MapperTicketsEntity.toTicketList(ticketsPaginationJpaRepository.findAll(pageable).iterator());
    }

    public Long getCount() {
        return ticketsJpaRepository.count();
    }

    @Transactional
    public void deleteTicket(Long ticketId){
        ticketsJpaRepository.deleteById(ticketId);
        return;
    }

    public List<Ticket> getTicketByFilter(Long userId, StatusEnum status) {
        return MapperTicketsEntity.toTicketList(ticketsJpaRepository.findByCustomFilter(userId, status).iterator());
    }

    @Transactional
    public Ticket updateTicket(TicketsDto ticket ){
        return MapperTicketsEntity.toTicket(ticketsJpaRepository.save(ticket));
    }
}
