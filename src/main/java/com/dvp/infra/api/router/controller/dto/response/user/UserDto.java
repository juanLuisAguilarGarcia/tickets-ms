package com.dvp.infra.api.router.controller.dto.response.user;

import com.dvp.infra.api.router.controller.dto.GenericResponseDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto extends GenericResponseDTO {
    private List<UserDataDto> data;
}
