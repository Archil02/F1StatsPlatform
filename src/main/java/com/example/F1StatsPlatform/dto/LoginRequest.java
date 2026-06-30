package com.example.F1StatsPlatform.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "ემაილი სავალდებულოა")
    @Email(message = "ემაილის ფორმატი არასწორია")
    private String email;

    @NotBlank(message = "პაროლი სავალდებულოა")
    private String password;
}