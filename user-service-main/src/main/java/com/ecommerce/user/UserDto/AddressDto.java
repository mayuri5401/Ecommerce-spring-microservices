package com.ecommerce.user.UserDto;

import com.ecommerce.user.Model.UserRole;
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
