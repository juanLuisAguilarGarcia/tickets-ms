package com.dvp.infra.adapter.db.jpa;

import com.dvp.domain.enums.StatusEnum;
import com.dvp.infra.adapter.db.entites.TicketsDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TicketsJpaRepository extends CrudRepository<TicketsDto, Long> {

     @Query(
             value = "SELECT * FROM tickets t WHERE t.status = IFNULL(?2, t.status) AND t.ticket_user_id = IFNULL(?1, t.ticket_user_id)",
             nativeQuery = true)
     List<TicketsDto> findByCustomFilter(Long userId, StatusEnum status);
}
