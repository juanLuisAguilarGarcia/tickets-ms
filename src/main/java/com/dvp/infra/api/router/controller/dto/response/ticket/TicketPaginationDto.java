package com.dvp.infra.api.router.controller.dto.response.ticket;

import com.dvp.infra.api.router.controller.dto.GenericResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketPaginationDto extends GenericResponseDTO {
    private Integer page;
    @JsonProperty("total_count")
    private Long totalCount;
    private List<TicketDataDto> data;
}
