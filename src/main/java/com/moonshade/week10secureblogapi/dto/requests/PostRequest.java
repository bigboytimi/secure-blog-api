package com.moonshade.week10secureblogapi.dto.requests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotBlank(message = "Category cannot be blank")
    private String category;
    @NotBlank(message = "Content cannot be blank")
    private String content;
}
