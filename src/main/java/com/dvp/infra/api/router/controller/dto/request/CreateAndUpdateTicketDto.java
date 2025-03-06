package com.dvp.infra.api.router.controller.dto.request;

import com.dvp.domain.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAndUpdateTicketDto {
    @JsonProperty("user_id")
    @NotNull
    private Long userId;
    @NotBlank
    private String description;
    @NotNull
    private StatusEnum status;
}
