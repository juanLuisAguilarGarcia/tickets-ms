package com.dvp.infra.adapter.db.jpa;

import com.dvp.infra.adapter.db.entites.TicketsDto;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface TicketsPaginationJpaRepository extends PagingAndSortingRepository<TicketsDto, Long> {

}
