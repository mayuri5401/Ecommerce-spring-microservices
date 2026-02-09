package com.main.EcomCore.app.dto;

import com.main.EcomCore.Model.Address;
import lombok.Data;


@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Address address;
}
