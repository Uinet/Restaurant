package com.github.uinet.project.dto;

import com.github.uinet.project.entity.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDTO {
    private String username;
    private String password;
    private boolean active;
    private Role role;
}
