package com.dvp.infra.adapter.db.entites;

import com.dvp.domain.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets" )
public class TicketsDto {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ticket_id")
    private Long ticketId;

    @Column(name = "ticket_user_id")
    private Long userId;

    @Column(length = 500)
    private String description;

    @Column
    private StatusEnum status;

    @Column(name = "create_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable = false)
    private Timestamp createAt;

    @Column(name = "update_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updateAt;
}
