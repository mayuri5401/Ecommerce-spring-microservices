package com.main.EcomCore.app.dto;

import com.main.EcomCore.Model.UserRole;
import lombok.Data;

@Data
public class AddressDto {
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;
    private UserRole role;
}
