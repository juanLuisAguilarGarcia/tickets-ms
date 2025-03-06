package com.dvp.infra.api.router.controller.dto.response.ticket;

import com.dvp.domain.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDataDto {
    @JsonProperty("ticket_id")
    private Long ticketId;
    @JsonProperty("user_id")
    private Long userId;
    private String description;
    private StatusEnum status;
    @JsonProperty("create_at")
    private Timestamp createAt;
    @JsonProperty("update_at")
    private Timestamp updateAt;
}
