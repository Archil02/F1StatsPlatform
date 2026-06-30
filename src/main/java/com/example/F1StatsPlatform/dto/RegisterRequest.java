package com.example.F1StatsPlatform.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "ემაილი სავალდებულოა")
    @Email(message = "ემაილის ფორმატი არასწორია")
    private String email;

    @NotBlank(message = "სახელი სავალდებულოა")
    private String firstName;

    @NotBlank(message = "გვარი სავალდებულოა")
    private String lastName;

    @NotBlank(message = "პაროლი სავალდებულოა")
    @Size(min = 8, message = "პაროლი მინიმუმ 8 სიმბოლო უნდა იყოს")
    private String password;
}