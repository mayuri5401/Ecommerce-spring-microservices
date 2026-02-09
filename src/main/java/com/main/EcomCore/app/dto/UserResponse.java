package com.main.EcomCore.app.dto;

import com.main.EcomCore.Model.Address;
import com.main.EcomCore.Model.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private AddressDto address;
}
