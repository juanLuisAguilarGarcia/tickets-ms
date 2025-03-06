package com.dvp.infra.api.router.controller.dto.response.ticket;

import com.dvp.infra.api.router.controller.dto.GenericResponseDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDto extends GenericResponseDTO {
    private List<TicketDataDto> data;
}
