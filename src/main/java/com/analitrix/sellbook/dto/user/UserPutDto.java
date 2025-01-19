package com.analitrix.sellbook.dto.user;

import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
public class UserPutDto {
    @Schema(defaultValue = "Cedula")
    private String documentType;
    @Schema(defaultValue = "123456789")
    private Long documentNumber;
    @Schema(defaultValue = "Pablo")
    private String name;
    @Schema(defaultValue = "Perez")
    private String surname;
    @Schema(defaultValue = "321456789")
    private Long phone;
    @Schema(defaultValue = "pablo.perez@example.com")
    private String mail;
    @Schema(defaultValue = "pass123456")
    private String password;
    @Schema(defaultValue = "Cra.50 #50-50")
    private String homeAddress;
}
