package com.online.shop.clientservice.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientRequest {

    @NotBlank
    @Size(min = 2, max = 128, message = "Name should be between 2 and 128 characters long")
    private String name;

    @NotBlank
    @Size(min = 2, max = 128, message = "Surname should be between 2 and 128 characters long")
    private String surname;

    @Email
    private String email;

    @NotBlank
    @Size(min = 4, max = 256, message = "Address should be between 4 and 256 characters long")
    private String address;
}
