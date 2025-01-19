package com.analitrix.sellbook.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGetDto {
    private String id;
    private String documentType;
    private Long documentNumber;
    private String name;
    private String surname;
    private Long phone;
    private String mail;
    private String homeAddress;
}
