package com.moonshade.week10secureblogapi.dto.requests;

import lombok.*;
import org.jetbrains.annotations.NotNull;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
