package com.dvp.infra.api.router.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAndUpdateUserDto {
    @JsonProperty("first_name")
    @NotBlank
    @NotNull
    private String firstName;
    @JsonProperty("last_name")
    @NotBlank
    @NotNull
    private String lastName;
}
