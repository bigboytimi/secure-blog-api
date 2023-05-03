package com.moonshade.week10secureblogapi.dto.response;

import com.moonshade.week10secureblogapi.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long id;
    @NotEmpty(message = "Name must not be empty")
    private String name;
    @NotBlank(message = "Password must not be blank")
    private String password;
    @NotNull(message = "Role cannot be blank")
    private Role role;
    @NotBlank(message = "Email cannot be blank")
    @Email
    private String email;
}
