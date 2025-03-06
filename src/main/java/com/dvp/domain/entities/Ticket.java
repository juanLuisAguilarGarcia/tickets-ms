package com.dvp.domain.entities;

import com.dvp.domain.enums.StatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Builder
public class Ticket {
    private Long ticketId;
    private Long userId;
    private String description;
    private StatusEnum status;
    private Timestamp createAt;
    private Timestamp updateAt;
}
