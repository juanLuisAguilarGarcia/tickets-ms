package com.dvp.domain.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Builder
public class User {
    private Long userId;
    private String firstName;
    private String lastName;
    private Timestamp createAt;
    private Timestamp updateAt;
}
