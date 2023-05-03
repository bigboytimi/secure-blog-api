package com.moonshade.week10secureblogapi.dto.requests;

import com.moonshade.week10secureblogapi.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String role;
}
